package br.com.fiap.postech.pagamento.application.domain.services

import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import org.json.JSONObject
import java.math.BigDecimal

const val PFI = "01"
const val COD_PAIS = "BR"
const val COD_MOEDA = "986"
const val ARRANJO_PAGAMENTO = "BR.GOV.BCB.PIX"
const val MCC = "0000"
const val COD_CAMPO_VALOR = "54"

class GenerateQrCodeHelper {

    fun generateQrCode(pedidoDTO: PedidoDTO): String {

        return generateInternal(newJSONObject(pedidoDTO, pedidoDTO.valor))
    }

    private fun generateInternal(jsonObj: JSONObject): String {
        val sb = StringBuilder()
        jsonObj.keySet().stream().sorted().forEach { key ->
            val valor = jsonObj[key]
            val str: String = encodeValue(key, valor)
            sb.append(leftPad(key)).append(strLenLeftPadded(str)).append(str)
        }

        return sb.toString()
    }

    private fun encodeValue(key: String, valor: Any): String {
        //Se o valor para a chave contém outro objeto, processa seus atributos recursivamente
        if (valor is JSONObject) return generateInternal(valor)

        //Se o valor é String ou um tipo primitivo
        return if (key == COD_CAMPO_VALOR) valor.toString() else removeSpecialChars(valor)
    }

    private fun newJSONObject(pedidoDTO: PedidoDTO, valor: BigDecimal): JSONObject {
        val jsonTemplate =
            """
            {
                '00': '%s',
                '26': {
                    '00': '%s',
                    '01': '%s',
                    '02': '%s'
                },
                '52': '%s',
                '53': '%s',
                '%s': '%s',
                '58': '%s',
                '59': '%s',
                '60': '%s',
                '62': {
                    '05': '%s'
                }
            }
            """.trimIndent()

        val json =
            jsonTemplate
                .formatted(
                    PFI, ARRANJO_PAGAMENTO, pedidoDTO.destinatarioPix.chaveDestinatario, pedidoDTO.destinatarioPix.descricao,
                    MCC, COD_MOEDA, COD_CAMPO_VALOR, formatNumber(valor), COD_PAIS,
                    pedidoDTO.destinatarioPix.nomeDestinatario, pedidoDTO.destinatarioPix.cidade, pedidoDTO.id
                )
        return JSONObject(json)
    }

    private fun crcChecksum(partialCode: String): String {
        var crc = 0xFFFF
        val byteArray = partialCode.toByteArray()
        for (b in byteArray) {
            crc = crc xor (b.toInt() shl 8)
            for (i in 0..7) {
                crc = if ((crc and 0x8000) == 0) crc shl 1
                else crc shl 1 xor 0x1021
            }
        }

        val decimal = crc and 0xFFFF
        return leftPad(Integer.toHexString(decimal), 4).toUpperCase()
    }

    private fun leftPad(code: String): String {
        return leftPad(code, 2)
    }

    private fun leftPad(code: String, len: Int): String {
        val format = "%1$" + len + "s"
        return format.formatted(code).replace(' ', '0')
    }

    private fun formatNumber(value: BigDecimal): String {
        return String.format("%.2f", value).formatted().replace(",", ".")
    }

    fun strLenLeftPadded(value: String): String {
        if (value.length > 99) {
            val msg = "Tamanho máximo dos valores dos campos deve ser 99. '%s' tem %d caracteres.".formatted(
                value,
                value.length
            )
            throw IllegalArgumentException(msg)
        }

        val len = value.length.toString()
        return leftPad(len)
    }

    private fun removeSpecialChars(value: Any): String {
        return value.toString().replace("[^a-zA-Z0-9\\-@\\.\\*\\s]".toRegex(), "")
    }

}
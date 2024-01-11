package br.com.fiap.postech.pagamento.adapter.inbound

import java.math.BigDecimal
import java.util.*

data class PedidoRequest(
    val id: UUID,
    val clienteId: UUID,
    val valor: BigDecimal,
    val destinatarioPix: DestinatarioPixRequest
)

data class DestinatarioPixRequest(
    val nomeDestinatario: String,
    val chaveDestinatario: String,
    val descricao: String,
    val cidade: String
)

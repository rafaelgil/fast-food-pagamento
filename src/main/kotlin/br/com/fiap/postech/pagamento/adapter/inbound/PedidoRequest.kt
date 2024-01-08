package br.com.fiap.postech.pagamento.adapter.inbound

import com.fasterxml.jackson.annotation.JsonCreator
import java.math.BigDecimal
import java.util.*

data class PedidoRequest(
    val id: UUID,
    val cliente: ClienteRequest,
    val valor: BigDecimal,
    val destinatarioPix: DestinatarioPixRequest
)

data class ClienteRequest(
    val id: UUID,
    val cpf: String,
    val nome: String,
    val email: String
)

data class DestinatarioPixRequest(
    val nomeDestinatario: String,
    val chaveDestinatario: String,
    val descricao: String,
    val cidade: String
)

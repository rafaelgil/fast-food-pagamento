package br.com.fiap.postech.pagamento.application.domain.dtos

import java.util.*

data class ClienteDTO (
    var id: UUID? = null,
    var cpf: String? = null,
    var nome: String? = null,
    var email: String? = null
)
package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.dtos

import java.util.*

class PagamentoEvent(
    val id: UUID,
    val status: String,
    val pedidoId: UUID
)
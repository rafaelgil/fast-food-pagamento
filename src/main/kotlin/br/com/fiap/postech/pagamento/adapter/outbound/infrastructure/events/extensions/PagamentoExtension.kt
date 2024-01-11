package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.extensions

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.dtos.PagamentoEvent
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento

fun Pagamento.toEvent() =
    PagamentoEvent(
        id = id!!,
        status = status.name,
        pedidoId = pedidoId
    )
package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.extension

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.entities.PagamentoEntity
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import java.util.*

fun Pagamento.toPagamentoEntity(id: UUID = UUID.randomUUID()): PagamentoEntity {
    return PagamentoEntity(
        id = this.id?.toString() ?: id.toString(),
        pedidoId = pedidoId.toString(),
        status = this.status.name,
        formaPagamento = this.formaPagamento,
        valor = this.valor,
    )
}

fun PagamentoEntity.toPagamento(): Pagamento {
    return Pagamento(
        id = UUID.fromString(id),
        pedidoId = UUID.fromString(pedidoId),
        formaPagamento = formaPagamento,
        valor = valor,
        status = StatusPagamento.valueOf(status)
    )
}
package br.com.fiap.postech.pagamento.application.ports.interfaces

import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import java.util.UUID

interface PagamentoServicePort {

    fun criarPagamento(pedidoDTO: PedidoDTO) : Pagamento

    fun atualizarPagamento(pagamentoId: String, statusPagamento: StatusPagamento): Pagamento

    fun buscarPagamentoPorId(pagamentoId: UUID): Pagamento

}
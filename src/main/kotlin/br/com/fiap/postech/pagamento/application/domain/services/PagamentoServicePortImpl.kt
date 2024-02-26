package br.com.fiap.postech.pagamento.application.domain.services

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer.QueueProducer
import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.extensions.toPagamento
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

class PagamentoServicePortImpl(
    private val pagamentoRepositoryPort: PagamentoRepositoryPort,
    private val queueProducer: QueueProducer
) : PagamentoServicePort {

    override fun criarPagamento(pedidoDTO: PedidoDTO): Pagamento {

        val qrCode = GenerateQrCodeHelper().generateQrCode(pedidoDTO)

        val pagamento = pedidoDTO.toPagamento(qrCode = qrCode)

        return pagamentoRepositoryPort.save(pagamento)
    }

    override fun atualizarPagamento(pagamentoId: String, statusPagamento: StatusPagamento): Pagamento {
        val pagamento = pagamentoRepositoryPort.findById(pagamentoId)

        pagamento.mudarStatus(statusPagamento)

        val pagamentoAtualizado = pagamentoRepositoryPort.save(pagamento)

        queueProducer.sendMessage(pagamentoAtualizado)

        return pagamentoAtualizado
    }

    override fun buscarPagamentoPorId(pagamentoId: UUID) = pagamentoRepositoryPort.findById(pagamentoId.toString())
}
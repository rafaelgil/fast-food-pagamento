package br.com.fiap.postech.pagamento.application.domain.services

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer.PagamentoPedidoErrorQueueProducer
import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer.PagamentoPedidoQueueProducer
import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.extensions.toPagamento
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import org.slf4j.LoggerFactory
import java.util.*

class PagamentoServicePortImpl(
    private val pagamentoRepositoryPort: PagamentoRepositoryPort,
    private val queueProducer: PagamentoPedidoQueueProducer,
    private val queueErrorProducer: PagamentoPedidoErrorQueueProducer
) : PagamentoServicePort {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun criarPagamento(pedidoDTO: PedidoDTO): Pagamento {

        val qrCode = GenerateQrCodeHelper().generateQrCode(pedidoDTO)

        val pagamento = pedidoDTO.toPagamento(qrCode = qrCode)

        return pagamentoRepositoryPort.save(pagamento)
    }

    override fun atualizarPagamento(pagamentoId: String, statusPagamento: StatusPagamento): Pagamento {
        logger.info("Atualizando pagamento ID $pagamentoId")

        val pagamento = pagamentoRepositoryPort.findById(pagamentoId)

        try {
            pagamento.mudarStatus(statusPagamento)

            val pagamentoAtualizado = pagamentoRepositoryPort.save(pagamento)

            queueProducer.sendMessage(pagamentoAtualizado)

            return pagamentoAtualizado
        }catch (e: Exception) {
            logger.error("Erro ao atualizar pagamento ID $pagamentoId enviando mensagem de cancelamento para fila de erro")

            pagamento.mudarStatus(StatusPagamento.CANCELADO)

            val pagamentoAtualizado = pagamentoRepositoryPort.save(pagamento)

            queueErrorProducer.sendMessage(pagamentoAtualizado)

            return pagamentoAtualizado
        }

    }

    override fun buscarPagamentoPorId(pagamentoId: UUID) = pagamentoRepositoryPort.findById(pagamentoId.toString())
}
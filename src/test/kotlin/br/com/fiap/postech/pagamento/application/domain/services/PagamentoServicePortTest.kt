package br.com.fiap.postech.pagamento.application.domain.services

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer.QueueProducer
import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.DestinatarioPix
import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*


internal class PagamentoServicePortTest {

    private val pagamentoRepositoryPort = mockk<PagamentoRepositoryPort>()

    private val queueProducer = mockk<QueueProducer>()

    private val pagamentoServicePort = PagamentoServicePortImpl(pagamentoRepositoryPort, queueProducer)


    @Test
    fun `Deveria salvar o pagamento do pedido`() {

        val pedido = PedidoDTO(
            id = UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"),
            clienteId = UUID.fromString("cad5f1c8-3cdf-445d-b954-f9fbb4cb8722"),
            valor = BigDecimal.TEN,
            destinatarioPix = DestinatarioPix(
                    nomeDestinatario = "Paula Fernandez",
                    chaveDestinatario = "0a524aae-3f60-4b5e-99d8-00657a16ecfe",
                    descricao = "PEDIDO 123",
                    cidade = "SAO PAULO"
                )
            )

        every { pagamentoRepositoryPort.save(any()) }.returnsArgument(0)

        val pagamento = pagamentoServicePort.criarPagamento(pedido)

        assertThat(pagamento)
            .isInstanceOf(Pagamento::class.java)
            .isNotNull()
        assertThat(pagamento.pedidoId)
            .isEqualTo(UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"))
        assertThat(pagamento.valor)
            .isEqualTo(BigDecimal.TEN)
        assertThat(pagamento.formaPagamento.tipo)
            .isEqualTo("QR_CODE")

        verify(exactly = 1) { pagamentoRepositoryPort.save(any()) }
    }

    @Test
    fun `Deveria atualizar o pagamento do pedido`() {

        val pagamento = Pagamento(
            id = UUID.fromString("5a5faefa-6eb0-48ab-99d8-a65dbe9472df"),
            pedidoId = UUID.randomUUID(),
            status = StatusPagamento.AGUARDANDO_PAGAMENTO,
            formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
            valor = BigDecimal.ONE
        )

        every { pagamentoRepositoryPort.findById(any()) } returns pagamento
        every { pagamentoRepositoryPort.save(any()) }.returnsArgument(0)
        every { queueProducer.sendMessage(any()) } returns Unit

        val pagamentoAtualizado = pagamentoServicePort.atualizarPagamento(pagamento.id.toString(), StatusPagamento.PAGO)

        assertThat(pagamentoAtualizado.status)
            .isEqualTo(StatusPagamento.PAGO)

        verify(exactly = 1) { pagamentoRepositoryPort.findById(any()) }
        verify(exactly = 1) { queueProducer.sendMessage(any())}
    }
}
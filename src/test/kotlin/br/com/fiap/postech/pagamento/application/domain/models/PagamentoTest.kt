package br.com.fiap.postech.pagamento.application.domain.models

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*


internal class  PagamentoTest {

    @Test
    fun `Deveria mudar de status para Pago`() {
        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedidoId = UUID.randomUUID(),
            status = StatusPagamento.AGUARDANDO_PAGAMENTO,
            formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
            valor = BigDecimal.ONE
        )

        pagamento.mudarStatus(StatusPagamento.PAGO)

        Assertions.assertThat(pagamento.status)
            .isEqualTo(StatusPagamento.PAGO)
    }

    @Test
    fun `Não deveria mudar de status quando pagamento já está Pago`() {

        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedidoId = UUID.randomUUID(),
            status = StatusPagamento.AGUARDANDO_PAGAMENTO,
            formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
            valor = BigDecimal.ONE
        )

        assertThatThrownBy {
            pagamento.mudarStatus(StatusPagamento.PAGO)
            pagamento.mudarStatus(StatusPagamento.PAGO)
        }.isInstanceOf(PagamentoException::class.java)
            .hasMessage("Pagamento ${pagamento.id} com status ${pagamento.status.name} não pode mudar para ${pagamento.status.name}")

    }

    @Test
    fun `Deveria mudar de status para Nao Pago`() {
        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedidoId = UUID.randomUUID(),
            status = StatusPagamento.AGUARDANDO_PAGAMENTO,
            formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
            valor = BigDecimal.ONE
        )

        pagamento.mudarStatus(StatusPagamento.NAO_PAGO)

        Assertions.assertThat(pagamento.status)
            .isEqualTo(StatusPagamento.NAO_PAGO)
    }

    @Test
    fun `Não deveria mudar de status quando pagamento já não está Pago`() {

        val pagamento = Pagamento(
            id = UUID.randomUUID(),
            pedidoId = UUID.randomUUID(),
            status = StatusPagamento.AGUARDANDO_PAGAMENTO,
            formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
            valor = BigDecimal.ONE
        )

        assertThatThrownBy {
            pagamento.mudarStatus(StatusPagamento.NAO_PAGO)
            pagamento.mudarStatus(StatusPagamento.NAO_PAGO)
        }.isInstanceOf(PagamentoException::class.java)
            .hasMessage("Pagamento ${pagamento.id} com status ${pagamento.status.name} não pode mudar para ${pagamento.status.name}")

    }

    @Test
    fun `Não deveria criar pagamento com status diferente de Aguardando Pagamento`() {
        assertThatThrownBy {
                Pagamento(
                    pedidoId = UUID.randomUUID(),
                    status = StatusPagamento.PAGO,
                    formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
                    valor = BigDecimal.ONE
            )
        }
        .isInstanceOf(PagamentoException::class.java)
        .hasMessage("Pagamento não pode inicializar com status diferente de AGUARDANDO_PAGAMENTO")
    }

    @Test
    fun `Não deveria criar pagamento com valor menor igual a ZERO`() {

        assertThatThrownBy {
            Pagamento(
                pedidoId = UUID.randomUUID(),
                status = StatusPagamento.AGUARDANDO_PAGAMENTO,
                formaPagamento = FormaPagamento(qrCodeValor = "QRCODE_"),
                valor = BigDecimal.ZERO
            )
        }
            .isInstanceOf(PagamentoException::class.java)
            .hasMessage("Pagamento não pode inicializar com valor menor igual a zero")
    }

}
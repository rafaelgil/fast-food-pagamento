package br.com.fiap.postech.pagamento.application.domain.models

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*


internal class  PagamentoTest {


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
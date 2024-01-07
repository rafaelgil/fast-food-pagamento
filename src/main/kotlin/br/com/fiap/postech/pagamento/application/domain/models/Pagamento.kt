package br.com.fiap.postech.pagamento.application.domain.models

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import java.math.BigDecimal
import java.util.*

data class Pagamento (
    var id: UUID? = null,
    var pedidoId: UUID,
    var status: StatusPagamento,
    var formaPagamento: FormaPagamento,
    var valor: BigDecimal,
) {

    init {
        validarInicializacaoPagamento()
    }

    fun mudarStatus(status: StatusPagamento) {
        when {
            status == StatusPagamento.PAGO -> {
                mudarStatusPago()
            }

            status == StatusPagamento.NAO_PAGO -> {
                mudarStatusNaoPago()
            }
        }
    }

    private fun validarInicializacaoPagamento() {
        if(this.id == null && this.status != StatusPagamento.AGUARDANDO_PAGAMENTO ){
            throw PagamentoException("Pagamento não pode inicializar com status diferente de ${StatusPagamento.AGUARDANDO_PAGAMENTO.name}")
        }

        if(this.valor.compareTo(BigDecimal.ZERO) <= 0 ){
            throw PagamentoException("Pagamento não pode inicializar com valor menor igual a zero")
        }

    }

    private fun mudarStatusPago(){
        if(this.status != StatusPagamento.AGUARDANDO_PAGAMENTO ){
            lancarErroMudancaStatusIncorreto(StatusPagamento.PAGO)
        }

        this.status = StatusPagamento.PAGO
    }

    private fun mudarStatusNaoPago(){
        if(this.status != StatusPagamento.AGUARDANDO_PAGAMENTO ){
            lancarErroMudancaStatusIncorreto(StatusPagamento.NAO_PAGO)
        }

        this.status = StatusPagamento.NAO_PAGO
    }

    private fun lancarErroMudancaStatusIncorreto(status: StatusPagamento) {
        throw PagamentoException("Pagamento ${this.id} com status ${this.status.name} não pode mudar para ${status.name}")
    }
}
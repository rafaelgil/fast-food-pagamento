package br.com.fiap.postech.pagamento.application.domain.extensions

import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import java.util.*


fun PedidoDTO.toPagamento(qrCode: String) =
    Pagamento(
        pedidoId = this.id,
        status = StatusPagamento.AGUARDANDO_PAGAMENTO,
        formaPagamento = FormaPagamento(qrCodeValor = qrCode),
        valor = this.valor
    )
package br.com.fiap.postech.pagamento.adapter.inbound

import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/notificacoes/pagamentos")
class NotificacaoPagamentoController(
    val pagamentoServicePort: PagamentoServicePort
) {

    @PutMapping("/{pagamentoId}")
    @ResponseStatus(HttpStatus.OK)
    fun atualizarPagamentoParaPago(@PathVariable pagamentoId: String): ResponseEntity<Void> {
        pagamentoServicePort.atualizarPagamento(pagamentoId = pagamentoId, statusPagamento = StatusPagamento.PAGO)
        return ResponseEntity.ok().build<Void>()
    }
}
package br.com.fiap.postech.pagamento.adapter.inbound

import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/pagamentos")
class PagamentoController(
    val pagamentoServicePort: PagamentoServicePort
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun gerarPagamento(@RequestBody pedidoRequest: PedidoRequest): PagamentoResponse {
        return pagamentoServicePort.criarPagamento(pedidoRequest.toPedidoDTO()).toPagamentoResponse()
    }

}
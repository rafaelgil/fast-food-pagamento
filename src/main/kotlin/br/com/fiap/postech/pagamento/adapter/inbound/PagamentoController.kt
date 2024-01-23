package br.com.fiap.postech.pagamento.adapter.inbound

import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/pagamentos")
class PagamentoController(
    val pagamentoServicePort: PagamentoServicePort
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun gerarPagamento(@RequestBody pedidoRequest: PedidoRequest): PagamentoResponse {
        logger.info("Gerando pagamento do pedido ${pedidoRequest.id}")

        return pagamentoServicePort.criarPagamento(pedidoRequest.toPedidoDTO()).toPagamentoResponse()
    }

}
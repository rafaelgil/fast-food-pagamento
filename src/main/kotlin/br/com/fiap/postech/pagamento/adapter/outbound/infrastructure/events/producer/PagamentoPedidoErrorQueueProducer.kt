package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.extensions.toErrorEvent
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class PagamentoPedidoErrorQueueProducer(
    @Value("\${aws.queue.notificacao-pagamento-error.name}")
    private val destination: String,
    @Autowired
    val queueMessagingTemplate: QueueMessagingTemplate
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendMessage(pagamento: Pagamento) {
        logger.info("Enviando cancelamento do pedido=${pagamento.pedidoId}")

        val msg: Message<String> = MessageBuilder.withPayload(ObjectMapper().writeValueAsString(pagamento.toErrorEvent()))
            .build()
        queueMessagingTemplate.send(destination, msg)

        logger.info("Mensagem de cancelamento do pedido=${pagamento.pedidoId} enviada com sucesso")
    }

}
package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.extensions.toEvent
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
class QueueProducer(
    @Value("\${aws.queue.name}")
    private val destination: String,
    @Autowired
    val queueMessagingTemplate: QueueMessagingTemplate
) {

    fun sendMessage(pagamento: Pagamento) {

        val msg: Message<String> = MessageBuilder.withPayload(ObjectMapper().writeValueAsString(pagamento.toEvent()))
            .build()
        queueMessagingTemplate.send(destination, msg)
    }

}
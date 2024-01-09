package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer

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


    fun sendMessage(messagePayload: String) {

        val msg: Message<String> = MessageBuilder.withPayload(messagePayload)
            .build()

        // Enviando a mensagem criada para o a fila "testQueue"
        queueMessagingTemplate.send(destination, msg)
    }

    /*
    fun sendMessage(pagamento: Pagamento) {
        //LOGGER.info("Generating event : {}", pagamento)
        println("Evento pagamento ${pagamento}" )
        var sendMessageRequest: SendMessageRequest? = null
        try {
            sendMessageRequest =
                SendMessageRequest().withQueueUrl("http://localhost:4566/000000000000/sample-queue.fifo")
                    .withMessageBody(ObjectMapper().writeValueAsString(pagamento))
                    .withMessageGroupId("Sample Message")
                    .withMessageDeduplicationId(UUID.randomUUID().toString())
            amazonSQS.sendMessage(sendMessageRequest)
            LOGGER.info("Event has been published in SQS.")
        } catch (e: JsonProcessingException) {
            println("ERRO 1" )
            //LOGGER.error("JsonProcessingException e : {} and stacktrace : {}", e.message, e)
        } catch (e: Exception) {
            println(e)
            //LOGGER.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.message, e)
        }
    }*/

}
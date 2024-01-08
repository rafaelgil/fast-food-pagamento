package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer

import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.internal.authentication.AwsCredentialHelper.LOGGER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class QueueProducer(
    @Value("\${aws.sqs.notificacao-pagamento-sync.url}")
    private val serviceQueueUrl: String,
    @Autowired
    private val amazonSQS: AmazonSQS
) {

    fun sendMessage(pagamento: Pagamento) {
        //LOGGER.info("Generating event : {}", pagamento)
        println("Evento pagamento ${pagamento}" )
        var sendMessageRequest: SendMessageRequest? = null
        try {
            sendMessageRequest =
                SendMessageRequest().withQueueUrl(serviceQueueUrl)
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
    }

}
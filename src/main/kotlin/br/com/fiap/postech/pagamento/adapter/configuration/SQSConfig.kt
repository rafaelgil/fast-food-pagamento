package br.com.fiap.postech.pagamento.adapter.configuration

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.services.sqs.SqsAsyncClient


@EnableSqs
@Configuration
class SQSConfig {

    @Primary
    @Bean(name = ["amazonSQSAsync"], destroyMethod = "shutdown")
    fun amazonSQSAsync(
        @Value("\${cloud.aws.end-point.uri}") sqsEndpoint: String,
        @Value("\${cloud.aws.region.static}") region: String
    ): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsEndpoint.replace("/notificacao-pagamento-sync", ""), region))
            .build()
    }

    @Bean
    fun sqsAsyncClient(@Value("\${cloud.aws.region.static}") region: String): SqsAsyncClient {
        return SqsAsyncClient.builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }

    @Bean
    fun queueMessagingTemplate(amazonSQSAsync: AmazonSQSAsync): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQSAsync)
    }

    @Bean
    fun queueMessageHandler(amazonSQSAsync: AmazonSQSAsync): QueueMessageHandler {
        val queueMsgHandlerFactory = QueueMessageHandlerFactory()
        queueMsgHandlerFactory.setAmazonSqs(amazonSQSAsync)
        val queueMessageHandler =
            queueMsgHandlerFactory.createQueueMessageHandler()
        val list: MutableList<HandlerMethodArgumentResolver> = ArrayList<HandlerMethodArgumentResolver>()
        val resolver: HandlerMethodArgumentResolver = PayloadMethodArgumentResolver(MappingJackson2MessageConverter())
        list.add(resolver)
        queueMessageHandler.setArgumentResolvers(list)

        return queueMessageHandler
    }
}
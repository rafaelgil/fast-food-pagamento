/*package br.com.fiap.postech.pagamento.adapter.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SQSConfig {
    @Value("\${cloud.aws.region.static}")
    private val region: String? = null

    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKeyId: String? = null

    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretAccessKey: String? = null

    @Value("\${cloud.aws.end-point.uri}")
    private val sqsUrl: String? = null

    @Bean
    fun amazonSQSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(sqsUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKeyId, secretAccessKey)))
            .build()
    }

    @Bean
    fun queueMessagingTemplate(amazonSQSAsync: AmazonSQSAsync): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQSAsync)
    }
}*/

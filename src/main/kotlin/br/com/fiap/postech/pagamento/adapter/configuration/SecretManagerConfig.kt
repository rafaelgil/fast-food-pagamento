package br.com.fiap.postech.pagamento.adapter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import java.net.URI

@Configuration
@Profile("!local")
class SecretManagerConfig {
    @Bean
    fun secretsManagerClient(): SecretsManagerClient {
        return SecretsManagerClient.builder()
            .region(Region.US_EAST_1)
            .build()
    }
}

@Configuration
@Profile("local")
class SecretManagerConfigLocal {
    @Bean
    fun secretsManagerClient(): SecretsManagerClient {
        return SecretsManagerClient.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.US_EAST_1)
            .credentialsProvider { AwsBasicCredentials.create("localstack", "localstack")}
            .build()
    }
}


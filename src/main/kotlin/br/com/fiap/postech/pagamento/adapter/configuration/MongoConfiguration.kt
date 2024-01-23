package br.com.fiap.postech.pagamento.adapter.configuration

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.repositories.PagamentoRepositorySpring
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.UuidRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(basePackageClasses = [PagamentoRepositorySpring::class])
@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoReactiveAutoConfiguration::class, MongoReactiveDataAutoConfiguration::class, MongoDataAutoConfiguration::class])
@Profile(value = ["!test"])
class MongoConfiguration : AbstractMongoClientConfiguration() {
    @Value("\${spring.data.mongodb.database}")
    private val databaseName: String? = null

    @Value("\${spring.data.mongodb.uri}")
    private val mongoUri: String? = null

    override fun getDatabaseName(): String {
        return databaseName!!
    }

    override fun mongoClient(): MongoClient {

        return MongoClients.create(
            MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(mongoUri!!))
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build()
        )
    }
}
package br.com.fiap.postech.pagamento.adpater.configuration

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.repositories.PagamentoRepositorySpring
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
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
@Profile(value = ["test"])
class MongoConfiguration : AbstractMongoClientConfiguration() {
    @Value("\${mongodb.database}")
    private val databaseName: String? = null

    @Value("\${mongodb.url}")
    private val connectionString: String? = null

    @Value("\${mongodb.username}")
    private val username: String? = null

    @Value("\${mongodb.password}")
    private val password: String? = null

    @Value("\${mongodb.authentication-database}")
    private val authenticationDatabase: String? = null

    override fun getDatabaseName(): String {
        return databaseName!!
    }

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(connectionString!!)
        val mongoClientSettings = if ("test" != username) MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .credential(MongoCredential.createCredential(username!!, authenticationDatabase!!, password!!.toCharArray()))

            .build() else MongoClientSettings.builder().applyConnectionString(connectionString).build()

        return MongoClients.create(mongoClientSettings)
    }
}
package br.com.fiap.postech.pagamento.adapter.configuration

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.events.producer.QueueProducer
import br.com.fiap.postech.pagamento.application.domain.services.PagamentoServicePortImpl
import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun pagamentoServicePort(pagamentoRepositoryPort: PagamentoRepositoryPort, queueProducer: QueueProducer): PagamentoServicePort {
        return PagamentoServicePortImpl(pagamentoRepositoryPort, queueProducer)
    }
}
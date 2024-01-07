package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.repositories.impl

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.repositories.PagamentoRepositorySpring
import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.extension.toPagamento
import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.extension.toPagamentoEntity
import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoNaoEncontradoException
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class PagamentoRepositoryImpl(
    val pagamentoRepositorySpring: PagamentoRepositorySpring
): PagamentoRepositoryPort {

    override fun save(pagamento: Pagamento) =
        pagamentoRepositorySpring.save(pagamento.toPagamentoEntity()).toPagamento()

    override fun findById(id: String) =
        pagamentoRepositorySpring.findById(id).orElseThrow { PagamentoNaoEncontradoException("Pagamento $id não encontrado") }.toPagamento()

}
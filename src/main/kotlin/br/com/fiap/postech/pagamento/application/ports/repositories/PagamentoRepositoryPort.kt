package br.com.fiap.postech.pagamento.application.ports.repositories

import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import java.util.UUID

interface PagamentoRepositoryPort {

    fun save(pagamento: Pagamento) : Pagamento

    fun findById(id: String): Pagamento

}
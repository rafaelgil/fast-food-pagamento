package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.repositories

import br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.entities.PagamentoEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PagamentoRepositorySpring: MongoRepository<PagamentoEntity, String> {

}
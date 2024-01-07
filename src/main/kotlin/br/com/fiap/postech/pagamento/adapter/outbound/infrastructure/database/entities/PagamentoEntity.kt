package br.com.fiap.postech.pagamento.adapter.outbound.infrastructure.database.entities

import br.com.fiap.postech.pagamento.application.domain.valueobject.FormaPagamento
import com.fasterxml.jackson.annotation.JsonRootName
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document("pagamento")
@JsonRootName("pagamento")
data class PagamentoEntity (
    @Id
    var id: String,
    var pedidoId: String,
    var status: String,
    var formaPagamento: FormaPagamento,
    var valor: BigDecimal,
)

package br.com.fiap.postech.pagamento.application.domain.dtos

import br.com.fiap.postech.pagamento.application.domain.valueobject.DestinatarioPix
import java.math.BigDecimal
import java.util.*

data class PedidoDTO(
    val id: UUID,
    val cliente: ClienteDTO?,
    val valor: BigDecimal,
    val destinatarioPix: DestinatarioPix
)
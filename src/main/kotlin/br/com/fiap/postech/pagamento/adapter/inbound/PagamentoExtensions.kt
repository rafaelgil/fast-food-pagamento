package br.com.fiap.postech.pagamento.adapter.inbound

import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.DestinatarioPix

fun PedidoRequest.toPedidoDTO() = 
    PedidoDTO(
        id = id,
        clienteId = clienteId,
        valor = valor,
        destinatarioPix = destinatarioPix.toDestinatarioPix()
    )

fun DestinatarioPixRequest.toDestinatarioPix() = 
    DestinatarioPix(
        nomeDestinatario = nomeDestinatario,
        chaveDestinatario = chaveDestinatario,
        descricao = descricao,
        cidade = cidade
    )

fun Pagamento.toPagamentoResponse() =
    PagamentoResponse(
        id = id!!,
        status = status.name
    )
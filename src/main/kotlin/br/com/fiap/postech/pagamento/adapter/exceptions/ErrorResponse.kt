package br.com.fiap.postech.pagamento.adapter.exceptions

data class ErrorResponse(
    var codigoHttp: Int,
    var mensagem: String
)
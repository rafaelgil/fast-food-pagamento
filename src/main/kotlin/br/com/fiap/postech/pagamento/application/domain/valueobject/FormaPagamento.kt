package br.com.fiap.postech.pagamento.application.domain.valueobject

data class FormaPagamento (
    val tipo: String = "QR_CODE",
    var qrCodeValor: String
)
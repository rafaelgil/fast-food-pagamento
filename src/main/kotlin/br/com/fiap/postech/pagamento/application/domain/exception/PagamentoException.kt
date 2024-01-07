package br.com.fiap.postech.pagamento.application.domain.exception

class PagamentoException (s: String, exception: Exception?): Exception(s, exception)
{
    constructor(s: String) : this(s, null)
}
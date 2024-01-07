package br.com.fiap.postech.pagamento.application.domain.exception

class PagamentoNaoEncontradoException (s: String, exception: Exception?): Exception(s, exception)
{
    constructor(s: String) : this(s, null)
}
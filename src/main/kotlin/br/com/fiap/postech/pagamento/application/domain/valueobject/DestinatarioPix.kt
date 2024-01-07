package br.com.fiap.postech.pagamento.application.domain.valueobject

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException

data class DestinatarioPix (
    val nomeDestinatario: String,
    val chaveDestinatario: String,
    val descricao: String,
    val cidade: String
) {
    init {
        validarInicializacaoDestinatario()
    }

    private fun validarInicializacaoDestinatario() {
        if(nomeDestinatario.length > 25) {
            throw PagamentoException("Nome do destinatário não pode ter mais que 25 caracteres. ${nomeDestinatario} tem ${nomeDestinatario.length} caracteres.");
        }

        if(chaveDestinatario.length > 77) {
            throw PagamentoException("Chave PIX do destinatário não pode ter mais que 77 caracteres. ${chaveDestinatario} tem ${chaveDestinatario.length} caracteres.");
        }

        if(descricao.length > 72) {
            throw PagamentoException("Descrição não pode ter mais que 72 caracteres. ${descricao} tem ${descricao.length} caracteres.");
        }

        if(cidade.length > 15) {
            throw PagamentoException("Cidade do remetente não pode ter mais que 15 caracteres. ${cidade} tem ${cidade.length} caracteres.");
        }
    }
}
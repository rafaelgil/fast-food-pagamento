package br.com.fiap.postech.pagamento.application.domain.models

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import br.com.fiap.postech.pagamento.application.domain.valueobject.DestinatarioPix
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


internal class DestinatarioPixTest {

    @Test
    fun `Não deveria aceitar nome de destinatario maior do que 25`() {
        val nome = "FULANO DE TAL TESTE TESTE TESTE TESTE TESTE TESTE"
        assertThatThrownBy {
                DestinatarioPix(
                    nomeDestinatario = nome,
                    chaveDestinatario = "IJIJIJIHUHIH",
                    descricao = "Teste",
                    cidade = "Sao Paulo"
            )
        }
        .isInstanceOf(PagamentoException::class.java)
        .hasMessage("Nome do destinatário não pode ter mais que 25 caracteres. ${nome} tem ${nome.length} caracteres.")
    }

    @Test
    fun `Não deveria aceitar chave pix maior do que 77`() {

        val nome = "FULANO TESTE"
        val chave = "JGRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRREGERRYYYYYYYYYYYYYYYYYYYYYYTHHGTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTUETTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTEETERTERTE"
        assertThatThrownBy {
            DestinatarioPix(
                nomeDestinatario = nome,
                chaveDestinatario = chave,
                descricao = "Teste",
                cidade = "Sao Paulo"
            )
        }
            .isInstanceOf(PagamentoException::class.java)
            .hasMessage("Chave PIX do destinatário não pode ter mais que 77 caracteres. ${chave} tem ${chave.length} caracteres.")
    }

    @Test
    fun `Não deveria aceitar descricao maior do que 72`() {

        val nome = "FULANO TESTE"
        val chave = "JGRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"
        val descricao = "EFGEUFGUEF EFHUEFUHEUF EFUHEFUHEUE FEFHEIUFHWOFHGEWRGHRUEGHEOIG ERGHEOIGERIGERIGJERGJ ERGIJERGIER GIERGJE RGJEIJG ERIGJERGIERJGIERJGIERG ERIGJ ERGJERGERIJGIEJG ERIGJER IGJIERGIERJGIERJGIERJ"
        assertThatThrownBy {
            DestinatarioPix(
                nomeDestinatario = nome,
                chaveDestinatario = chave,
                descricao = descricao,
                cidade = "Sao Paulo"
            )
        }
            .isInstanceOf(PagamentoException::class.java)
            .hasMessage("Descrição não pode ter mais que 72 caracteres. ${descricao} tem ${descricao.length} caracteres.")
    }

    @Test
    fun `Não deveria aceitar nome de cidade maior do que 15`() {

        val nome = "FULANO TESTE"
        val chave = "1a9adb79-9b57-4c0a-b8a4-4bf9b8ea75e2"
        val descricao = "PEDIDO 12121"
        val cidade = "AASSSSSHHSU UHUSHUSHSHU SHUSHUSH SHSUHS"
        assertThatThrownBy {
            DestinatarioPix(
                nomeDestinatario = nome,
                chaveDestinatario = chave,
                descricao = descricao,
                cidade = cidade
            )
        }
            .isInstanceOf(PagamentoException::class.java)
            .hasMessage("Cidade do remetente não pode ter mais que 15 caracteres. ${cidade} tem ${cidade.length} caracteres.")
    }
}
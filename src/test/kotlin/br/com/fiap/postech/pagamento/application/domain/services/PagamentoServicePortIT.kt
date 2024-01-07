package br.com.fiap.postech.pagamento.application.domain.services

import br.com.fiap.postech.pagamento.application.domain.dtos.ClienteDTO
import br.com.fiap.postech.pagamento.application.domain.dtos.PedidoDTO
import br.com.fiap.postech.pagamento.application.domain.models.Pagamento
import br.com.fiap.postech.pagamento.application.domain.valueobject.DestinatarioPix
import br.com.fiap.postech.pagamento.application.domain.valueobject.StatusPagamento
import br.com.fiap.postech.pagamento.application.ports.interfaces.PagamentoServicePort
import br.com.fiap.postech.pagamento.application.ports.repositories.PagamentoRepositoryPort
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PagamentoServicePortIT(
    @Autowired
    val pagamentoServicePort: PagamentoServicePort,
    @Autowired
    val pagamentoRepositoryPort: PagamentoRepositoryPort
) {

    companion object {
        private lateinit var mongodExecutable: MongodExecutable

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            val mongodConfig = MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(Net("localhost", 47047, Network.localhostIsIPv6()))
                .build()

            val starter = MongodStarter.getDefaultInstance()
            mongodExecutable = starter.prepare(mongodConfig)
            mongodExecutable.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            mongodExecutable.stop()
        }
    }

    @Test
    fun `Deveria salvar o pagamento do pedido`() {

        val pedido = PedidoDTO(
            id = UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"),
            cliente = ClienteDTO(id = UUID.randomUUID(), cpf = "16731725129", "Joao Pedro", "joao@mock.com"),
            valor = BigDecimal.TEN,
            destinatarioPix = DestinatarioPix(
                nomeDestinatario = "Paula Fernandez",
                chaveDestinatario = "0a524aae-3f60-4b5e-99d8-00657a16ecfe",
                descricao = "PEDIDO 123",
                cidade = "SAO PAULO"
            )
        )

        val pagamento = pagamentoServicePort.criarPagamento(pedido)

        Assertions.assertThat(pagamento)
            .isInstanceOf(Pagamento::class.java)
            .isNotNull()
        Assertions.assertThat(pagamento.id)
            .isNotNull()
        Assertions.assertThat(pagamento.pedidoId)
            .isEqualTo(UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"))
        Assertions.assertThat(pagamento.valor)
            .isEqualTo(BigDecimal.TEN)
        Assertions.assertThat(pagamento.formaPagamento.tipo)
            .isEqualTo("QR_CODE")
        Assertions.assertThat(pagamento.formaPagamento.qrCodeValor)
            .isNotNull()
        Assertions.assertThat(pagamento.status)
            .isEqualTo(StatusPagamento.AGUARDANDO_PAGAMENTO)

    }

    @Test
    fun `Deveria buscar o pagamento pelo ID salvo na base`() {

        val pedido = PedidoDTO(
            id = UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"),
            cliente = ClienteDTO(id = UUID.randomUUID(), cpf = "16731725129", "Joao Pedro", "joao@mock.com"),
            valor = BigDecimal.TEN,
            destinatarioPix = DestinatarioPix(
                nomeDestinatario = "Paula Fernandez",
                chaveDestinatario = "0a524aae-3f60-4b5e-99d8-00657a16ecfe",
                descricao = "PEDIDO 123",
                cidade = "SAO PAULO"
            )
        )

        val pagamento = pagamentoServicePort.criarPagamento(pedido)

        val pagamentoSalvo = pagamentoRepositoryPort.findById(pagamento.id.toString())

        Assertions.assertThat(pagamentoSalvo)
            .isInstanceOf(Pagamento::class.java)
            .isNotNull()
        Assertions.assertThat(pagamentoSalvo.id)
            .isNotNull()
        Assertions.assertThat(pagamentoSalvo.pedidoId)
            .isEqualTo(UUID.fromString("5e537dc4-0aaa-442d-a8ea-052251afd011"))
        Assertions.assertThat(pagamentoSalvo.valor)
            .isEqualTo(BigDecimal.TEN)
        Assertions.assertThat(pagamentoSalvo.formaPagamento.tipo)
            .isEqualTo("QR_CODE")
        Assertions.assertThat(pagamentoSalvo.formaPagamento.qrCodeValor)
            .isNotNull()
        Assertions.assertThat(pagamentoSalvo.status)
            .isEqualTo(StatusPagamento.AGUARDANDO_PAGAMENTO)

    }

}
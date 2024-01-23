package br.com.fiap.postech.pagamento.adpater.inbound

import br.com.fiap.postech.pagamento.adapter.inbound.DestinatarioPixRequest
import br.com.fiap.postech.pagamento.adapter.inbound.PagamentoController
import br.com.fiap.postech.pagamento.adapter.inbound.PedidoRequest
import com.fasterxml.jackson.databind.ObjectMapper
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*


@WebMvcTest(PagamentoController::class)
@ActiveProfiles("test")
internal class PagamentoControllerIT {
    @Autowired
    var mockMvc: MockMvc? = null

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

    var openMocks: AutoCloseable? = null

    @Test
    fun `Deveria salvar o pagamento do pedido`() {

        val pedidoRequest = PedidoRequest(
            id = UUID.randomUUID(),
            clienteId = UUID.fromString("cad5f1c8-3cdf-445d-b954-f9fbb4cb8722"),
            valor = BigDecimal.TEN,
            destinatarioPix = DestinatarioPixRequest(
                nomeDestinatario = "Loja Fast Food",
                chaveDestinatario = "0efb985b-0686-4365-984c-359d6f7b01c6",
                descricao = "PEDIDO 123",
                cidade = "SAO PAULO"
            )
        )

        mockMvc!!.perform(
            post("/v1/pagamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pedidoRequest))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.status").value("AGUARDANDO_PAGAMENTO"))
    }

    fun asJsonString(obj: Any?): String {
        try {
            return ObjectMapper().writeValueAsString(obj)
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }
}
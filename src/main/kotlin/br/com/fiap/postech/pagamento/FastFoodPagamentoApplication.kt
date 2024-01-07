package br.com.fiap.postech.pagamento

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = arrayOf("br.com.fiap.postech.pagamento.*"))
class FastFoodPagamentoApplication

fun main(args: Array<String>) {
	runApplication<FastFoodPagamentoApplication>(*args)
}
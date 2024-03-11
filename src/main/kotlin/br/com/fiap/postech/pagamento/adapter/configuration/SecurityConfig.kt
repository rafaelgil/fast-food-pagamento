package br.com.fiap.postech.pagamento.adapter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .headers { headers ->
                headers
                    .frameOptions { it.disable() }
                    .httpStrictTransportSecurity { it.disable() }
                    .cacheControl { it.disable() }
                    .xssProtection { it.disable() }
                    .contentTypeOptions {  }
            }
            .sessionManagement { }
            .csrf { it.disable() }
            .cors { it.disable() }

        return http.build()
    }
}
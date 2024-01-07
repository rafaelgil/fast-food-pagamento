package br.com.fiap.postech.pagamento.adapter.exceptions

import br.com.fiap.postech.pagamento.application.domain.exception.PagamentoException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice() {

    @ExceptionHandler(PagamentoException::class)
    fun handleIllegalArgumentException(ex: PagamentoException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }

}
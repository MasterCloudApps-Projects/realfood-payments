package es.urjc.realfood.payments.infrastructure.api.rest

import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<Error> {
        val statusCode = statusCodeByException[ex::class] ?: HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity
            .status(statusCode)
            .body(Error.from(ex, statusCode))
    }

    data class Error(val reason: String?, val code: Int) {
        companion object {
            fun from(ex: Exception, code: HttpStatus): Error {
                return Error(ex.message, code.value())
            }
        }
    }

    companion object {
        val statusCodeByException = mapOf(
            IllegalArgumentException::class to HttpStatus.BAD_REQUEST,
            EntityNotFoundException::class to HttpStatus.NOT_FOUND,
            HttpMessageNotReadableException::class to HttpStatus.BAD_REQUEST
        )
    }

}
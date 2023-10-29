package br.com.catalogoprodutos.adapters.http.error

import br.com.catalogoprodutos.application.produto.exceptions.ProdutoNaoEncontradoException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.UUID

private val LOGGER = KotlinLogging.logger { }

@ControllerAdvice
class ErrorHandler() {
    @ExceptionHandler(Exception::class)
    fun handlerException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ex.toServerResponse()
    }
}

fun Throwable.toResponse(): Pair<HttpStatus, ErrorResponse> =
    when (this) {
        is ProdutoNaoEncontradoException -> toResponse(
            id = this.produtoId,
            statusCode = HttpStatus.NOT_FOUND,
        )

        else -> {
            toResponse(
                statusCode = HttpStatus.BAD_REQUEST,
            )
        }
    }

fun Throwable.toResponse(
    id: UUID? = null,
    statusCode: HttpStatus,
    message: String = this.message ?: "",
): Pair<HttpStatus, ErrorResponse> {
    val response = ErrorResponse(
        id = id,
        message = message,
    )

    val fullMessage = "[${statusCode.value()}] [${this.javaClass.simpleName}] $message"
    if (statusCode.is5xxServerError) {
        LOGGER.error(this) { fullMessage }
    } else {
        LOGGER.warn { fullMessage }
    }

    return statusCode to response
}

fun Throwable.toServerResponse(): ResponseEntity<ErrorResponse> {
    val (statusCode, response) = toResponse()
    return ResponseEntity.status(statusCode).body(response)
}

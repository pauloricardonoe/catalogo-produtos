package br.com.catalogoprodutos.adapter.error

import java.util.UUID

data class ErrorResponse(
    val id: UUID? = null,
    val message: String,
)

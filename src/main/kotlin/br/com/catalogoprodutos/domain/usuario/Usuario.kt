package br.com.catalogoprodutos.domain.usuario

import java.util.UUID

data class Usuario(
    val id: UUID = UUID.randomUUID(),
    val nome: String,
    val email: String,
    val password: String,
)

package br.com.catalogoprodutos.adapters.http.security.request

import kotlinx.serialization.Serializable

@Serializable
data class Credenciais(
    val email: String,
    val senha: String,
)

package br.com.catalogoprodutos.application.usuario

import br.com.catalogoprodutos.domain.usuario.Usuario
import kotlinx.serialization.Serializable

@Serializable
class UsuarioQuery(
    val id: String,
    val nome: String,
    val email: String,
)

fun Usuario.toQuery() = UsuarioQuery(
    id = id.toString(),
    nome = nome,
    email = email,
)

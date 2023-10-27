package br.com.catalogoprodutos.application.usuario

import br.com.catalogoprodutos.domain.usuario.Usuario
import kotlinx.serialization.Serializable

@Serializable
class UsuarioCreateCommand(
    val nome: String,
    val email: String,
    val password: String,
)

fun UsuarioCreateCommand.toUsuario(encoderPassword: EncoderPassword) = Usuario(
    nome = nome,
    email = email,
    password = encoderPassword.encode(password),
)

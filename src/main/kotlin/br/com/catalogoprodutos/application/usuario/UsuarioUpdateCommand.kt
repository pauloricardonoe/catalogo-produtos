package br.com.catalogoprodutos.application.usuario

import br.com.catalogoprodutos.domain.usuario.Usuario
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class UsuarioUpdateCommand(
    val nome: String,
    val email: String,
    val password: String,
)

fun UsuarioUpdateCommand.toUsuario(usuarioId: UUID, encoderPassword: EncoderPassword) = Usuario(
    id = usuarioId,
    nome = nome,
    email = email,
    password = encoderPassword.encode(password),
)

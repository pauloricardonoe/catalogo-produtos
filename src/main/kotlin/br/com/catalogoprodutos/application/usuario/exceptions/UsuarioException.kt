package br.com.catalogoprodutos.application.usuario.exceptions

import java.util.UUID

sealed class UsuarioException(message: String) : Exception(message) {
    abstract val usuarioId: UUID?
}

data class UsuarioNaoEncontradoException(
    override val usuarioId: UUID? = null,
    val username: String? = null,
) : UsuarioException("Usuário ${usuarioId ?: username} não encontrado")


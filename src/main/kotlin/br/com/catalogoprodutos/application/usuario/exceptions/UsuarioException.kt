package br.com.catalogoprodutos.application.usuario.exceptions

import java.util.UUID

sealed class UsuarioException(message: String) : Exception(message) {
    abstract val usuarioId: UUID?
}

data class UsuarioNaoEncontradoException(
    override val usuarioId: UUID?,
) : UsuarioException("Usuário $usuarioId não encontrado")

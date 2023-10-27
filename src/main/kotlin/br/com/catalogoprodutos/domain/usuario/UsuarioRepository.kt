package br.com.catalogoprodutos.domain.usuario

import java.util.UUID

interface UsuarioRepository {

    fun findAll(): List<Usuario>

    fun findById(usuarioId: UUID): Usuario?

    fun inserir(usuario: Usuario): Boolean

    fun atualizar(usuario: Usuario): Boolean

    fun excluir(usuarioId: UUID): Boolean
}

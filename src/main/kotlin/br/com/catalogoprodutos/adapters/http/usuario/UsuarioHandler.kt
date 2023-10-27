package br.com.catalogoprodutos.adapters.http.usuario

import br.com.catalogoprodutos.application.usuario.UsuarioCreateCommand
import br.com.catalogoprodutos.application.usuario.UsuarioQuery
import br.com.catalogoprodutos.application.usuario.UsuarioService
import br.com.catalogoprodutos.application.usuario.UsuarioUpdateCommand
import br.com.catalogoprodutos.application.usuario.toQuery
import br.com.catalogoprodutos.domain.usuario.Usuario
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UsuarioHandler(
    val usuarioService: UsuarioService,
) {
    fun findAll(): ResponseEntity<List<UsuarioQuery>> {
        val usuarios = usuarioService.findAll().map { it.toQuery() }
        return ResponseEntity.ok(usuarios)
    }

    fun findById(usuarioId: String): ResponseEntity<UsuarioQuery> {
        val usuario = usuarioService.findById(UUID.fromString(usuarioId)).toQuery()
        return ResponseEntity.ok(usuario)
    }

    fun inserir(usuarioCreateCommand: UsuarioCreateCommand): ResponseEntity<UsuarioQuery> {
        val usuario = usuarioService.inserir(usuarioCreateCommand)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario.toQuery())
    }

    fun atualizar(usuarioUpdateCommand: UsuarioUpdateCommand, usuarioId: String): ResponseEntity<UsuarioQuery> {
        val usuario = usuarioService.atualizar(usuarioUpdateCommand, UUID.fromString(usuarioId))
        return ResponseEntity.ok(usuario.toQuery())
    }

    fun excluir(usuarioId: String): ResponseEntity<String> {
        usuarioService.excluir(usuarioId = UUID.fromString(usuarioId))
        return ResponseEntity.noContent().build()
    }
}

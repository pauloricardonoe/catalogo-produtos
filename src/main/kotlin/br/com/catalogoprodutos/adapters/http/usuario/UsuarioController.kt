package br.com.catalogoprodutos.adapters.http.usuario

import br.com.catalogoprodutos.application.usuario.UsuarioCreateCommand
import br.com.catalogoprodutos.application.usuario.UsuarioQuery
import br.com.catalogoprodutos.application.usuario.UsuarioUpdateCommand
import br.com.catalogoprodutos.domain.usuario.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
class UsuarioController(
    private val usuarioHandler: UsuarioHandler,
) {
    @GetMapping("/usuarios")
    fun findAll(): ResponseEntity<List<UsuarioQuery>> {
        return usuarioHandler.findAll()
    }

    @GetMapping("/usuarios/{usuarioId:$UUID_REGEX}")
    fun findById(@PathVariable usuarioId: String): ResponseEntity<UsuarioQuery> {
        return usuarioHandler.findById(usuarioId)
    }

    @PostMapping("/usuarios")
    fun inserir(@RequestBody usuario: UsuarioCreateCommand): ResponseEntity<UsuarioQuery> {
        return usuarioHandler.inserir(usuario)
    }

    @PutMapping("/usuarios/{usuarioId:$UUID_REGEX}")
    fun atualizar(
        @RequestBody usuario: UsuarioUpdateCommand,
        @PathVariable usuarioId: String,
    ): ResponseEntity<UsuarioQuery> {
        return usuarioHandler.atualizar(usuario, usuarioId)
    }

    @DeleteMapping("/usuarios/{usuarioId:$UUID_REGEX}")
    fun excluir(@PathVariable usuarioId: String): ResponseEntity<String> {
        return usuarioHandler.excluir(usuarioId)
    }
}

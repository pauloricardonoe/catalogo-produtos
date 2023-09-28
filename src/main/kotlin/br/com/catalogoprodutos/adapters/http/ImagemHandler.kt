package br.com.catalogoprodutos.adapters.http

import br.com.catalogoprodutos.application.produto.imagem.ImagemService
import br.com.catalogoprodutos.produto.imagem.Imagem
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ImagemHandler(
    private val imagemService: ImagemService
) {
    fun findAll(produtoId: String): ResponseEntity<Set<Imagem>>{
        return ResponseEntity.ok(imagemService.findAll(UUID.fromString(produtoId)))
    }

    fun findById(produtoId: String, imagemId: String): ResponseEntity<Imagem> {
        return ResponseEntity.ok(imagemService.findById(UUID.fromString(produtoId), UUID.fromString(imagemId)))
    }

    fun deleteById(produtoId: String, imagemId: String): ResponseEntity<Imagem> {
        imagemService.deleteById(UUID.fromString(produtoId), UUID.fromString(imagemId))
        return ResponseEntity.noContent().build()
    }
}
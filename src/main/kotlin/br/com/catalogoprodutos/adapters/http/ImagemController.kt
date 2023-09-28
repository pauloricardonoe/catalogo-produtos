package br.com.catalogoprodutos.adapters.http

import br.com.catalogoprodutos.produto.imagem.Imagem
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
class ImagemController(
    private val imagemHandler: ImagemHandler,
) {

    @GetMapping("/produtos/{produtoId:$UUID_REGEX}/imagens")
    fun findAll(@PathVariable produtoId: String): ResponseEntity<Set<Imagem>> {
        return imagemHandler.findAll(produtoId)
    }

    @GetMapping("/produtos/{produtoId:$UUID_REGEX}/imagens/{imagemId:$UUID_REGEX}")
    fun findById(@PathVariable produtoId: String, @PathVariable imagemId: String): ResponseEntity<Imagem> {
        return imagemHandler.findById(produtoId, imagemId)
    }

    @DeleteMapping("/produtos/{produtoId:$UUID_REGEX}/imagens/{imagemId:$UUID_REGEX}")
    fun deleteById(@PathVariable produtoId: String, @PathVariable imagemId: String): ResponseEntity<Imagem> {
        return imagemHandler.deleteById(produtoId, imagemId)
    }

    @PostMapping("/produtos/{produtoId:$UUID_REGEX}/imagens")
    fun save(@PathVariable produtoId: String): ResponseEntity<Imagem> {
        //return imagemHandler.deleteById(produtoId, imagemId)
        return ResponseEntity.ok(
            Imagem(
                id = UUID.randomUUID(),
                url = ""
        )
        )
    }
}

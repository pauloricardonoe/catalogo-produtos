package br.com.catalogoprodutos.adapters.http

import br.com.catalogoprodutos.application.produto.ProdutoCreateCommand
import br.com.catalogoprodutos.application.produto.ProdutoUpdateCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import br.com.catalogoprodutos.domain.produto.Produto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
class ProdutoController(
    private val produtoHandler: ProdutoHandler
) {

    @GetMapping("/produtos")
    fun findAll(): ResponseEntity<List<Produto>>{
        return produtoHandler.findAll()
    }

    @GetMapping("/produtos/{produtoId:$UUID_REGEX}")
    fun findById(@PathVariable produtoId: String): ResponseEntity<Produto>{
        return produtoHandler.findById(produtoId)
    }

    @PostMapping("/produtos")
    fun inserir(@RequestBody produto: ProdutoCreateCommand): ResponseEntity<Produto> {
        return produtoHandler.inserir(produto)
    }

    @PutMapping("/produtos/{produtoId:$UUID_REGEX}")
    fun atualizar(@RequestBody produto: ProdutoUpdateCommand, @PathVariable produtoId: String): ResponseEntity<Produto> {
        return produtoHandler.atualizar(produto, produtoId)
    }

    @DeleteMapping("/produtos/{produtoId:$UUID_REGEX}")
    fun excluir(@PathVariable produtoId: String): ResponseEntity<String> {
        return produtoHandler.excluir(produtoId)
    }
}
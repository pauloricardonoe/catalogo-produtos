package br.com.catalogoprodutos.adapters.http.produto

import br.com.catalogoprodutos.application.produto.ProdutoCreateCommand
import br.com.catalogoprodutos.application.produto.ProdutoService
import br.com.catalogoprodutos.application.produto.ProdutoUpdateCommand
import br.com.catalogoprodutos.domain.produto.Produto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProdutoHandler(
    private val produtoService: ProdutoService
) {
    fun findAll(): ResponseEntity<List<Produto>> {
        val produtos = produtoService.findAll()
        return ResponseEntity.ok(produtos)
    }

    fun findById(produtoId: String): ResponseEntity<Produto> {
        val produto = produtoService.findById(UUID.fromString(produtoId))
        return ResponseEntity.ok(produto)
    }

    fun inserir(produtoCreateCommand: ProdutoCreateCommand): ResponseEntity<Produto> {
        val produto = produtoService.inserir(produtoCreateCommand)
        return ResponseEntity.status(HttpStatus.CREATED).body(produto)
    }

    fun atualizar(produtoUpdateCommand: ProdutoUpdateCommand, produtoId: String): ResponseEntity<Produto> {
        val produto = produtoService.atualizar(produtoUpdateCommand, UUID.fromString(produtoId))
        return ResponseEntity.ok(produto)
    }

    fun excluir(produtoId: String): ResponseEntity<String> {
        produtoService.excluir(produtoId = UUID.fromString(produtoId))
        return ResponseEntity.noContent().build()
    }
}
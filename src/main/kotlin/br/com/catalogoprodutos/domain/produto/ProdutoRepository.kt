package br.com.catalogoprodutos.domain.produto

import java.util.UUID

interface ProdutoRepository {

    fun findAll(): List<Produto>

    fun findById(produtoId: UUID): Produto?

    fun inserir(produto: Produto): Boolean

    fun atualizar(produto: Produto): Boolean

    fun excluir(produtoId: UUID): Boolean
}
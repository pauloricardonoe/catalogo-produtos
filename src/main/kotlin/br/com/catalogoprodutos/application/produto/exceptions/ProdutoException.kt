package br.com.catalogoprodutos.application.produto.exceptions

import java.util.UUID

sealed class ProdutoException(message: String) : Exception(message) {
    abstract val produtoId: UUID?
}

data class ProdutoNaoEncontradoException(
    override val produtoId: UUID?
) : ProdutoException("Produto $produtoId não encontrado")
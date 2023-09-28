package br.com.catalogoprodutos.domain.produto

import br.com.catalogoprodutos.produto.ProdutoStatus
import br.com.catalogoprodutos.produto.imagem.Imagem
import java.util.UUID

data class Produto(
    val id: UUID = UUID.randomUUID(),
    val sku: String,
    val nome: String,
    val descricao: String,
    val unidade: String,
    val peso: Double,
    val estoque: Double,
    val status: ProdutoStatus,
    val imagens: Set<Imagem>
)

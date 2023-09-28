package br.com.catalogoprodutos.application.produto

import br.com.catalogoprodutos.application.produto.imagem.ImagemCreateCommand
import br.com.catalogoprodutos.application.produto.imagem.toImagem
import br.com.catalogoprodutos.domain.produto.Produto
import br.com.catalogoprodutos.produto.ProdutoStatus
import kotlinx.serialization.Serializable

@Serializable
data class ProdutoCreateCommand(
    val sku: String,
    val nome: String,
    val descricao: String,
    val unidade: String,
    val peso: Double,
    val estoque: Double,
    val status: ProdutoStatus,
    val imagens: Set<ImagemCreateCommand>,
)

fun ProdutoCreateCommand.toProduto() = Produto(
    sku = sku,
    nome = nome,
    descricao = descricao,
    unidade = unidade,
    peso = peso,
    estoque = estoque,
    status = status,
    imagens = imagens.map { it.toImagem() }.toSet()
)

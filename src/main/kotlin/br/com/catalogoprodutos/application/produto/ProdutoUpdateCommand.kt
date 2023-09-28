package br.com.catalogoprodutos.application.produto

import br.com.catalogoprodutos.application.produto.imagem.ImagemUpdateCommand
import br.com.catalogoprodutos.application.produto.imagem.toImagem
import br.com.catalogoprodutos.domain.produto.Produto
import br.com.catalogoprodutos.produto.ProdutoStatus
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProdutoUpdateCommand(
    val sku: String,
    val nome: String,
    val descricao: String,
    val unidade: String,
    val peso: Double,
    val estoque: Double,
    val status: ProdutoStatus,
    val imagens: Set<ImagemUpdateCommand>,
)

fun ProdutoUpdateCommand.toProduto(id: UUID) = Produto(
    id = id,
    sku = sku,
    nome = nome,
    descricao = descricao,
    unidade = unidade,
    peso = peso,
    estoque = estoque,
    status = status,
    imagens = imagens.map { imagem -> imagem.toImagem() }.toSet()
)


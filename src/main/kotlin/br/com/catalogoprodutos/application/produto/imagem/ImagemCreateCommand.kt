package br.com.catalogoprodutos.application.produto.imagem

import br.com.catalogoprodutos.produto.imagem.Imagem
import kotlinx.serialization.Serializable

@Serializable
data class ImagemCreateCommand(
    val url: String,
)
fun ImagemCreateCommand.toImagem() = Imagem(
    url = url,
)

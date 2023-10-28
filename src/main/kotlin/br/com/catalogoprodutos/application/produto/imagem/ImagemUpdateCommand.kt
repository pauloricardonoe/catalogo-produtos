package br.com.catalogoprodutos.application.produto.imagem

import br.com.catalogoprodutos.produto.imagem.Imagem
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ImagemUpdateCommand(
    val id: String? = null,
    val url: String,
)
fun ImagemUpdateCommand.toImagem() = Imagem(
    id = UUID.fromString(id ?: UUID.randomUUID().toString()),
    url = url,
)

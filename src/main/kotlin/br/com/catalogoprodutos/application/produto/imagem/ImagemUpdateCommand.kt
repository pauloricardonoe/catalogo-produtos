package br.com.catalogoprodutos.application.produto.imagem

import br.com.catalogoprodutos.application.produto.serializer.UuidSerializer
import br.com.catalogoprodutos.produto.imagem.Imagem
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ImagemUpdateCommand(
    @Serializable(UuidSerializer::class) val id: UUID,
    val url: String,
)
fun ImagemUpdateCommand.toImagem() = Imagem(
    id = id,
    url = url,
)

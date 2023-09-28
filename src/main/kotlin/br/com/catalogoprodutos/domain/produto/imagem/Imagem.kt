package br.com.catalogoprodutos.produto.imagem

import java.util.UUID

data class Imagem(
    val id: UUID = UUID.randomUUID(),
    val url: String,
)

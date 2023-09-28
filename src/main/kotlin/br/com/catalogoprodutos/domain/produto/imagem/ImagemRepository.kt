package br.com.catalogoprodutos.produto.imagem

import java.util.UUID

interface ImagemRepository {
    fun findAll(produtoId: UUID): Set<Imagem>
    fun findById(produtoId: UUID, imagemId: UUID): Imagem?
    fun inserir(imagem: Imagem, produtoId: UUID): Boolean
    fun atualizar(imagem: Imagem, produtoId: UUID): Boolean
    fun excluir(produtoId: UUID, imagemId: UUID): Boolean
}

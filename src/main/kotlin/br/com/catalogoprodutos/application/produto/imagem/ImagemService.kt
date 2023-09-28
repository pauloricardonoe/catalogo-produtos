package br.com.catalogoprodutos.application.produto.imagem

import br.com.catalogoprodutos.produto.imagem.Imagem
import br.com.catalogoprodutos.produto.imagem.ImagemRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ImagemService(
    private val imagemRepository: ImagemRepository
) {

    fun findAll(produtoId: UUID): Set<Imagem>{
        return imagemRepository.findAll(produtoId)
    }

    fun findById(produtoId: UUID, imagemId: UUID): Imagem? {
        return imagemRepository.findById(produtoId, imagemId)
    }

    fun deleteById(produtoId: UUID, imagemId: UUID) {
        imagemRepository.excluir(produtoId, imagemId)
    }
}
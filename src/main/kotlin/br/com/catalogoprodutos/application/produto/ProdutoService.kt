package br.com.catalogoprodutos.application.produto

import br.com.catalogoprodutos.application.produto.exceptions.ProdutoNaoEncontradoException
import br.com.catalogoprodutos.domain.produto.Produto
import br.com.catalogoprodutos.domain.produto.ProdutoRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository
) {

    fun findAll(): List<Produto>{
        return produtoRepository.findAll()
    }

    fun findById(produtoId: UUID): Produto{
        return produtoRepository.findById(produtoId) ?: throw ProdutoNaoEncontradoException(produtoId)
    }

    fun inserir(produto: ProdutoCreateCommand): Produto{
        val produtoDomain = produto.toProduto()
        produtoRepository.inserir(produto = produtoDomain)
        return findById(produtoDomain.id)
    }

    fun atualizar(produto: ProdutoUpdateCommand, produtoId: UUID): Produto {
        produtoRepository.findById(produtoId = produtoId) ?: throw ProdutoNaoEncontradoException(produtoId)

        produtoRepository.atualizar(produto.toProduto(produtoId))
        return findById(produtoId = produtoId)
    }

    fun excluir(produtoId: UUID){
        produtoRepository.findById(produtoId = produtoId) ?: throw ProdutoNaoEncontradoException(produtoId)
        produtoRepository.excluir(produtoId)
    }
}
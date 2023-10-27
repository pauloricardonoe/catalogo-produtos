package br.com.catalogoprodutos.adapters.jdbc.produtos

import br.com.catalogoprodutos.adapters.jdbc.produtos.ProdutoSqlExpressions.sqlDeleteById
import br.com.catalogoprodutos.adapters.jdbc.produtos.ProdutoSqlExpressions.sqlInsertProdutos
import br.com.catalogoprodutos.adapters.jdbc.produtos.ProdutoSqlExpressions.sqlSelectAll
import br.com.catalogoprodutos.adapters.jdbc.produtos.ProdutoSqlExpressions.sqlSelectById
import br.com.catalogoprodutos.adapters.jdbc.produtos.ProdutoSqlExpressions.sqlUpdateProduto
import br.com.catalogoprodutos.domain.produto.Produto
import br.com.catalogoprodutos.domain.produto.ProdutoRepository
import br.com.catalogoprodutos.produto.ProdutoStatus
import br.com.catalogoprodutos.produto.imagem.ImagemRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class ProdutosJDBCRepository(
    private val db: NamedParameterJdbcOperations,
    private val imagemRepository: ImagemRepository,
) : ProdutoRepository {

    private companion object {
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Produto> {
        val produtos = try {
            db.query(sqlSelectAll(), rowMapper())
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar os produtos: ${ex.message}" }
            throw ex
        }

        return produtos
    }

    override fun findById(produtoId: UUID): Produto? {
        val produto = try {
            val params = MapSqlParameterSource("id", produtoId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar o produto: ${ex.message}" }
            throw ex
        }
        return produto
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun inserir(produto: Produto): Boolean {
        try {
            val params = parametros(produto)
            val linhasAfetadas = db.update(sqlInsertProdutos(), params)
            produto.imagens.forEach { imagem ->
                imagemRepository.inserir(imagem, produto.id)
            }
            return linhasAfetadas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao inserir o produto: ${ex.message}" }
            throw ex
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun atualizar(produto: Produto): Boolean {
        try {
            val params = parametros(produto)
            val linhasAfetadas = db.update(sqlUpdateProduto(), params)
            produto.imagens.forEach { imagem ->
                if (imagemRepository.findById(produtoId = produto.id, imagemId = imagem.id) != null) {
                    imagemRepository.atualizar(imagem, produto.id)
                } else {
                    imagemRepository.inserir(imagem, produto.id)
                }
            }
            return linhasAfetadas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao atualizar o produto: ${ex.message}" }
            throw ex
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun excluir(produtoId: UUID): Boolean {
        try {
            imagemRepository.findAll(produtoId).forEach {
                imagemRepository.excluir(produtoId = produtoId, imagemId = it.id)
            }
            val params = MapSqlParameterSource("id", produtoId)
            val linhasExcluidas = db.update(sqlDeleteById(), params)
            return linhasExcluidas == 1
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao excluir o produto: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Produto> { rs, _ ->
        val produtoId = UUID.fromString(rs.getString("id"))
        val imagens = imagemRepository.findAll(produtoId)
        Produto(
            id = produtoId,
            sku = rs.getString("sku"),
            nome = rs.getString("nome"),
            descricao = rs.getString("descricao"),
            unidade = rs.getString("unidade"),
            peso = rs.getDouble("peso"),
            estoque = rs.getDouble("estoque"),
            status = ProdutoStatus.valueOf(rs.getString("status")),
            imagens = imagens,
        )
    }

    private fun parametros(produto: Produto): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", produto.id)
        params.addValue("nome", produto.nome)
        params.addValue("sku", produto.sku)
        params.addValue("descricao", produto.descricao)
        params.addValue("unidade", produto.unidade)
        params.addValue("peso", produto.peso)
        params.addValue("estoque", produto.estoque)
        params.addValue("status", produto.status.name)
        return params
    }
}

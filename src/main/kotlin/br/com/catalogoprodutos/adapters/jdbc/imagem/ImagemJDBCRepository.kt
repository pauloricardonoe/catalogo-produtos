package br.com.catalogoprodutos.adapter.jdbc.produto.imagem

import br.com.catalogoprodutos.adapter.jdbc.produto.imagem.ImagemSqlExpressions.sqlDeleteById
import br.com.catalogoprodutos.adapter.jdbc.produto.imagem.ImagemSqlExpressions.sqlInsert
import br.com.catalogoprodutos.adapter.jdbc.produto.imagem.ImagemSqlExpressions.sqlSelectAll
import br.com.catalogoprodutos.adapter.jdbc.produto.imagem.ImagemSqlExpressions.sqlSelectById
import br.com.catalogoprodutos.adapter.jdbc.produto.imagem.ImagemSqlExpressions.sqlUpdate
import br.com.catalogoprodutos.produto.imagem.Imagem
import br.com.catalogoprodutos.produto.imagem.ImagemRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
open class ImagemJDBCRepository(private val db: NamedParameterJdbcTemplate) : ImagemRepository {

    private companion object {
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(produtoId: UUID): Set<Imagem> {
        val params = MapSqlParameterSource("produtoid", produtoId)
        return db.query(sqlSelectAll(), params, rowMapper()).toSet()
    }

    override fun findById(produtoId: UUID, imagemId: UUID): Imagem? {
        try {
            val params = MapSqlParameterSource()
            params.addValue("id", imagemId)
            params.addValue("produto_id", produtoId)
            return db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar a imagem: ${ex.message}" }
            throw ex
        }
    }

    override fun inserir(imagem: Imagem, produtoId: UUID): Boolean {
        try {
            val params = parametros(
                imagem = imagem,
                produtoId = produtoId,
            )
            val linhasInseridas = db.update(sqlInsert(), params)
            return linhasInseridas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao inserir a imagem: ${ex.message}" }
            throw ex
        }
    }

    override fun atualizar(imagem: Imagem, produtoId: UUID): Boolean {
        try {
            val params = parametros(
                imagem = imagem,
                produtoId = produtoId,
            )
            val linhasInseridas = db.update(sqlUpdate(), params)
            return linhasInseridas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao atualizar a imagem: ${ex.message}" }
            throw ex
        }
    }
    override fun excluir(produtoId: UUID, imagemId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource()
            params.addValue("id", imagemId)
            params.addValue("produto_id", produtoId)
            val linhasInseridas = db.update(sqlDeleteById(), params)
            return linhasInseridas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao excluir a imagem: ${ex.message}" }
            throw ex
        }
    }

    private fun parametros(imagem: Imagem, produtoId: UUID): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", imagem.id)
        params.addValue("url", imagem.url)
        params.addValue("produto_id", produtoId)
        return params
    }

    private fun rowMapper() = RowMapper<Imagem> { rs, _ ->
        Imagem(
            id = UUID.fromString(rs.getString("id")),
            url = rs.getString("url"),
        )
    }
}

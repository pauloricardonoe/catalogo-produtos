package br.com.catalogoprodutos.adapters.jdbc.usuarios

import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlDeleteById
import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlInsert
import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlSelectAll
import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlSelectByEmail
import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlSelectById
import br.com.catalogoprodutos.adapters.jdbc.usuarios.UsuarioSqlExpressions.sqlUpdate
import br.com.catalogoprodutos.domain.usuario.Usuario
import br.com.catalogoprodutos.domain.usuario.UsuarioRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class UsuarioJdbcRepository(
    private val db: NamedParameterJdbcOperations,
) : UsuarioRepository {
    private companion object {
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Usuario> {
        val usuarios = try {
            db.query(sqlSelectAll(), rowMapper())
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar os usuarios: ${ex.message}" }
            throw ex
        }

        return usuarios
    }

    override fun findById(usuarioId: UUID): Usuario? {
        val usuario = try {
            val params = MapSqlParameterSource("id", usuarioId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar o usuario: ${ex.message}" }
            throw ex
        }
        return usuario
    }

    override fun findByEmail(email: String): Usuario? {
        val usuario = try {
            val params = MapSqlParameterSource("email", email)
            db.query(sqlSelectByEmail(), params, rowMapper()).firstOrNull()
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar o usuario: ${ex.message}" }
            throw ex
        }
        return usuario
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun inserir(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlInsert(), params)
            return linhasAfetadas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao inserir o usuario: ${ex.message}" }
            throw ex
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun atualizar(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlUpdate(), params)
            return linhasAfetadas > 0
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao atualizar o usuario: ${ex.message}" }
            throw ex
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun excluir(usuarioId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", usuarioId)
            val linhasExcluidas = db.update(sqlDeleteById(), params)
            return linhasExcluidas == 1
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao excluir o usuario: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Usuario> { rs, _ ->
        val usuarioId = UUID.fromString(rs.getString("id"))
        Usuario(
            id = usuarioId,
            nome = rs.getString("nome"),
            email = rs.getString("email"),
            password = rs.getString("password"),
        )
    }

    private fun parametros(usuario: Usuario): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", usuario.id)
        params.addValue("nome", usuario.nome)
        params.addValue("email", usuario.email)
        params.addValue("password", usuario.password)
        return params
    }
}

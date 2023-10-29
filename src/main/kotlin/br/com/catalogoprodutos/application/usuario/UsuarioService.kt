package br.com.catalogoprodutos.application.usuario

import br.com.catalogoprodutos.adapters.http.security.UserDetailsSpringSecurity
import br.com.catalogoprodutos.application.usuario.exceptions.UsuarioNaoEncontradoException
import br.com.catalogoprodutos.domain.usuario.Usuario
import br.com.catalogoprodutos.domain.usuario.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val encoderPassword: EncoderPassword,
) : UserDetailsService {
    fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun findById(usuarioId: UUID): Usuario {
        return usuarioRepository.findById(usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId = usuarioId)
    }

    fun findByEmail(email: String): Usuario? {
        return usuarioRepository.findByEmail(email)
    }

    fun inserir(usuario: UsuarioCreateCommand): Usuario {
        val usuarioDomain = usuario.toUsuario(encoderPassword = encoderPassword)
        usuarioRepository.inserir(usuario = usuarioDomain)
        return findById(usuarioDomain.id)
    }

    fun atualizar(usuario: UsuarioUpdateCommand, usuarioId: UUID): Usuario {
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId = usuarioId)

        usuarioRepository.atualizar(usuario.toUsuario(usuarioId = usuarioId, encoderPassword = encoderPassword))
        return findById(usuarioId = usuarioId)
    }

    fun excluir(usuarioId: UUID) {
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId = usuarioId)
        usuarioRepository.excluir(usuarioId)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository.findByEmail(username ?: "")
            ?: throw UsuarioNaoEncontradoException(username = username)

        return UserDetailsSpringSecurity(usuario)
    }
}

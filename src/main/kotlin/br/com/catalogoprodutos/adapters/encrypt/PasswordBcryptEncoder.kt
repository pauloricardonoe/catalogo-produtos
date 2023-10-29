package br.com.catalogoprodutos.adapters.encrypt

import br.com.catalogoprodutos.application.usuario.EncoderPassword
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordBcryptEncoder(
    private val passwordEncoder: PasswordEncoder,
) : EncoderPassword {
    override fun encode(senha: String): String {
        return passwordEncoder.encode(senha)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}

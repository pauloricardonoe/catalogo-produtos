package br.com.catalogoprodutos.adapters.http.security

import br.com.catalogoprodutos.adapters.http.security.exceptions.CredenciaisInvalidasException
import br.com.catalogoprodutos.adapters.http.security.request.Credenciais
import br.com.catalogoprodutos.adapters.http.security.response.Token
import br.com.catalogoprodutos.application.usuario.EncoderPassword
import br.com.catalogoprodutos.application.usuario.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val usuarioService: UsuarioService,
    private val encoderPassword: EncoderPassword,
    private val jwtUtil: JWTUtil,
) {

    @PostMapping("/login")
    fun auth(@RequestBody credenciais: Credenciais): ResponseEntity<Token> {
        val usuario = usuarioService.findByEmail(credenciais.email) ?: throw CredenciaisInvalidasException()

        if (!encoderPassword.matches(credenciais.senha, usuario.password)) {
            throw CredenciaisInvalidasException()
        }

        val accessToken = jwtUtil.generateToken(usuario = usuario) ?: throw Exception("Erro ao gerar o token")
        return ResponseEntity.ok().body(Token(accessToken))
    }
}

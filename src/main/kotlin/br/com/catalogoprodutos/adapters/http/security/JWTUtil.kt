package br.com.catalogoprodutos.adapters.http.security

import br.com.catalogoprodutos.application.usuario.UsuarioService
import br.com.catalogoprodutos.domain.usuario.Usuario
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JWTUtil(
    private val usuarioService: UsuarioService,
) {

    private val expiration: Long = 24 * 60 * 60 * 1000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(usuario: Usuario): String? {
        return Jwts.builder()
            .id(usuario.id.toString())
            .subject(usuario.email)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSecretKey(), SIG.HS512)
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwt)
            true
        } catch (e: JwtException) {
            throw e
        }
    }

    fun getSecretKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwt).payload.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}

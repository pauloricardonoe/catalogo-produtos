package br.com.catalogoprodutos.adapters.http.security

import br.com.catalogoprodutos.adapters.http.error.toResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthenticationFilter(
    private val jwtUtil: JWTUtil,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            if (request.headerNames.toList().contains("authorization")) {
                val token = request.getHeader("Authorization")
                val jwt = getTokenDetail(token)
                if (jwtUtil.isValid(jwt)) {
                    val authentication = jwtUtil.getAuthentication(jwt)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            val errorResponse = e.toResponse()
            val format = Json { ignoreUnknownKeys = true }
            response.status = errorResponse.first.value()
            response.addHeader("Content-Type", "application/json")
            response.writer.write(format.encodeToString(errorResponse.second))
        }
    }

    private fun getTokenDetail(token: String?): String? {
        return token?.let { jwt ->
            jwt.startsWith("Bearer ")
            jwt.substring(7, jwt.length)
        }
    }
}

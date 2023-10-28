package br.com.catalogoprodutos.application.usuario

interface EncoderPassword {
    fun encode(senha: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}

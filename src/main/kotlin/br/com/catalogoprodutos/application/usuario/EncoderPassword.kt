package br.com.catalogoprodutos.application.usuario

interface EncoderPassword {
    fun encode(senha: String): String
}

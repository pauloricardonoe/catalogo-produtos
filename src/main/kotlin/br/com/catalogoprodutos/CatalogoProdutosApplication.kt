package br.com.catalogoprodutos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin

@SpringBootApplication()
class CatalogoProdutosApplication

fun main(args: Array<String>) {
//    val message = Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().encoded)
//    println(message)
    runApplication<CatalogoProdutosApplication>(*args)
}

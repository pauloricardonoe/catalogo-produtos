package br.com.catalogoprodutos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatalogoProdutosApplication

fun main(args: Array<String>) {
	runApplication<CatalogoProdutosApplication>(*args)
}

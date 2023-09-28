package br.com.catalogoprodutos.adapters.jdbc

object ProdutoSqlExpressions {

    fun sqlSelectAll() = """
        SELECT id, 
               nome,
               sku,
               descricao,
               unidade,
               peso,
               estoque,
               status
        from produto
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT id, 
               nome,
               sku,
               descricao,
               unidade,
               peso,
               estoque,
               status
        FROM produto 
        WHERE id = :id
    """.trimIndent()

    fun sqlInsertProdutos() = """
       INSERT INTO produto(
           id,
           nome,
           sku, 
           descricao, 
           unidade, 
           peso, 
           estoque, 
           status)
        values (
           :id,
           :nome,
           :sku, 
           :descricao, 
           :unidade, 
           :peso, 
           :estoque, 
           :status
        )
    """.trimIndent()

    fun sqlUpdateProduto() = """
        UPDATE produto
        set nome = :nome,
            sku = :sku, 
            descricao = :descricao, 
            unidade = :unidade, 
            peso = :peso, 
            estoque = :estoque, 
            status = :status                
        where id = :id
    """.trimIndent()

    fun sqlDeleteById() = """
        DELETE FROM produto where id = :id
    """.trimIndent()
}
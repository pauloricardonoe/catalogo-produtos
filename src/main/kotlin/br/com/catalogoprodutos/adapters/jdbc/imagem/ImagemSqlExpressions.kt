package br.com.catalogoprodutos.adapter.jdbc.produto.imagem

object ImagemSqlExpressions {

    fun sqlInsert() = """
       INSERT INTO imagem(
           id,
           url,
           produto_id)
        values (
           :id,
           :url,
           :produto_id)
    """.trimIndent()

    fun sqlUpdate() = """
        UPDATE imagem
        set id = :id, 
            url = :url,                 
            produto_id = :produto_id                 
        where id = :id
    """.trimIndent()

    fun sqlSelectById() = """
        SELECT id, 
               url 
        FROM imagem 
        WHERE id = :id and produto_id = :produto_id
    """.trimIndent()

    fun sqlSelectAll() = """
        select id,
               url
        from imagem 
        where produto_id = :produtoid
    """.trimIndent()

    fun sqlDeleteById() = """
        DELETE FROM imagem WHERE id = :id and produto_id = :produto_id
    """.trimIndent()
}

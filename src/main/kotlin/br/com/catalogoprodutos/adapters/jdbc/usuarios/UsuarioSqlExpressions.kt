package br.com.catalogoprodutos.adapters.jdbc.usuarios

object UsuarioSqlExpressions {

    fun sqlSelectAll() = """
        select 
            id,
            nome,
            email,
            password
        from usuario
    """.trimIndent()

    fun sqlSelectById() = """
        select 
            id,
            nome,
            email,
            password
        from usuario
        WHERE id = :id
    """.trimIndent()

    fun sqlSelectByEmail() = """
        select 
            id,
            nome,
            email,
            password
        from usuario
        WHERE email = :email
    """.trimIndent()

    fun sqlInsert() = """
       insert into usuario 
       (id,
        nome,
        email,
        password)
        values 
        (
        :id,
        :nome,
        :email,
        :password
        )
    """.trimIndent()

    fun sqlUpdate() = """
        UPDATE usuario
        set nome = :nome,
            email = :email,
            password = :password      
        where id = :id
    """.trimIndent()

    fun sqlDeleteById() = """
        DELETE FROM usuario where id = :id
    """.trimIndent()
}

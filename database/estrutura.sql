SET ROLE "catalogo-produtos";
SET STATEMENT_TIMEOUT TO '300s';

create table produto
(
    id   uuid not null
        constraint produto_pk
            primary key,
    nome varchar(50),
    sku varchar(20),
    descricao varchar(100),
    unidade varchar(2),
    peso decimal,
    estoque decimal,
    status varchar(10)
);

alter table produto
    owner to "catalogo-produtos";


create table imagem
(
    id         uuid not null
        constraint imagem_pk
            primary key,
    url        text not null,
    produto_id uuid not null
        constraint produto_imagem_fk
            references produto
);

alter table imagem
    owner to "catalogo-produtos";
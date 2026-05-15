CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100),
    chefe BOOLEAN
);

CREATE TABLE espetinhos (
    id SERIAL PRIMARY KEY,
    sabor VARCHAR(100),
    preco DOUBLE PRECISION,
    estoque INT
);
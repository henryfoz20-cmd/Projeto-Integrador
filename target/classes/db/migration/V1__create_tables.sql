CREATE TABLE clientes (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(100),
                          email VARCHAR(100)
);

CREATE TABLE categorias (
                            id SERIAL PRIMARY KEY,
                            nome VARCHAR(100)
);

CREATE TABLE produtos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(100),
                          preco DOUBLE PRECISION,
                          categoria_id INT,
                          FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE pedidos (
                         id SERIAL PRIMARY KEY,
                         total DOUBLE PRECISION,
                         cliente_id INT,
                         FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE pedido_produto (
                                pedido_id INT,
                                produto_id INT,
                                PRIMARY KEY (pedido_id, produto_id),
                                FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
                                FOREIGN KEY (produto_id) REFERENCES produtos(id)
);


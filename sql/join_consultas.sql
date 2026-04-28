-- 1. INNER JOIN entre clientes e pedidos
SELECT
    c.id AS cliente_id,
    c.nome AS cliente,
    p.id AS pedido_id,
    p.total
FROM clientes c
    INNER JOIN pedidos p ON c.id = p.cliente_id;

-- 2. LEFT JOIN entre clientes e pedidos
SELECT
    c.id AS cliente_id,
    c.nome AS cliente,
    p.id AS pedido_id,
    p.total
FROM clientes c
    LEFT JOIN pedidos p ON c.id = p.cliente_id;

-- 3. INNER JOIN entre categorias e produtos
SELECT
    cat.id AS categoria_id,
    cat.nome AS categoria,
    prod.id AS produto_id,
    prod.nome AS produto,
    prod.preco,
    prod.estoque
FROM categorias cat
    INNER JOIN produtos prod ON cat.id = prod.categoria_id;

-- 4. JOIN entre pedidos e produtos
SELECT
    ped.id AS pedido_id,
    prod.id AS produto_id,
    prod.nome AS produto,
    prod.preco
FROM pedidos ped
    INNER JOIN pedido_produto pp ON ped.id = pp.pedido_id
    INNER JOIN produtos prod ON prod.id = pp.produto_id;

-- 5. JOIN completo: cliente + pedido + produto
SELECT
    c.nome AS cliente,
    ped.id AS pedido_id,
    prod.nome AS produto,
    prod.preco
FROM clientes c
    INNER JOIN pedidos ped ON c.id = ped.cliente_id
    INNER JOIN pedido_produto pp ON ped.id = pp.pedido_id
    INNER JOIN produtos prod ON prod.id = pp.produto_id;

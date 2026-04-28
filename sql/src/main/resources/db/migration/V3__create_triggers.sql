
-- TRIGGER 1: Impede estoque negativo ao vincular produto a pedido

CREATE OR REPLACE FUNCTION verificar_estoque()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT estoque FROM produtos WHERE id = NEW.produto_id) <= 0 THEN
        RAISE EXCEPTION 'Estoque insuficiente para o produto: %', NEW.produto_id;
END IF;

UPDATE produtos SET estoque = estoque - 1 WHERE id = NEW.produto_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_verificar_estoque
    BEFORE INSERT ON pedido_produto
    FOR EACH ROW EXECUTE FUNCTION verificar_estoque();

-- TRIGGER 2: Impede exclusão de cliente com pedidos ativos

CREATE OR REPLACE FUNCTION proteger_cliente()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM pedidos WHERE cliente_id = OLD.id) THEN
        RAISE EXCEPTION 'Cliente possui pedidos e não pode ser excluído: %', OLD.id;
END IF;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_proteger_cliente
    BEFORE DELETE ON clientes
    FOR EACH ROW EXECUTE FUNCTION proteger_cliente();

-- TRIGGER 3: Impede preço negativo ao inserir ou atualizar produto

CREATE OR REPLACE FUNCTION validar_preco()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.preco < 0 THEN
        RAISE EXCEPTION 'Preço não pode ser negativo: %', NEW.preco;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_validar_preco
    BEFORE INSERT OR UPDATE ON produtos
                         FOR EACH ROW EXECUTE FUNCTION validar_preco();
package sistemapedidos.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {

    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/sistema_pedidos",
                        "postgres",
                        "lopolopo2000"
                )
                // Permite aplicar migrations fora da ordem original do histórico
                .outOfOrder(true)
                .load();

        flyway.migrate();
    }
}

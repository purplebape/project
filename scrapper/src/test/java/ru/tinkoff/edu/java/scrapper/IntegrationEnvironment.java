package ru.tinkoff.edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IntegrationEnvironment {
    private static final Logger log = LoggerFactory.getLogger(IntegrationEnvironment.class);
    private static final PostgreSQLContainer<?> SQL_CONTAINER;
    private static final String MASTER_PATH = "migrations/master.xml";

    static {
        SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");
        SQL_CONTAINER.start();
        migrateDatabase();
    }

    private static void migrateDatabase() {
        try (Connection connection = getConnection()) {
            Path path = new File("../").toPath().toAbsolutePath().normalize();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(MASTER_PATH, new DirectoryResourceAccessor(path), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            log.error("Failed to initialize database", e);
            throw new RuntimeException(e);
        }
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                SQL_CONTAINER.getJdbcUrl(),
                SQL_CONTAINER.getUsername(),
                SQL_CONTAINER.getPassword()
        );
    }
}

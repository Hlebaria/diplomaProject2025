package com.choiceApp.MyChoiceApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.Test;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionTests {

    private String dbUser;
    private String dbPassword;
    private String dbName;

    private Connection c;

    @Test
    public void GetDbVars() {

        Dotenv dotenv = Dotenv.configure().filename("database.env").load();

        dbUser = dotenv.get("POSTGRES_USER");
        dbPassword = dotenv.get("POSTGRES_PASSWORD");
        dbName = dotenv.get("POSTGRES_DB");

        dbPassword = dbPassword.replace("'", "");

        System.out.println("POSTGRES_USER: " + dbUser);
        System.out.println("POSTGRES_PASSWORD: " + dbPassword);
        System.out.println("POSTGRES_DB: " + dbName);

        assertNotNull(dbUser);
        assertNotNull(dbPassword);
        assertNotNull(dbName);
    }

    @Test
    public void Connection() throws SQLException {

        GetDbVars();

        PGConnectionPoolDataSource p = new PGConnectionPoolDataSource();
        p.setPassword(dbPassword);
        p.setUser(dbUser);
        p.setDatabaseName(dbName);
        p.setServerNames(new String[]{"127.0.0.1"});
        p.setPortNumbers(new int[]{6543});
        c = p.getConnection();

    }

    @Test
    public void printer() {
        System.out.println("\ncheck");
    }

}

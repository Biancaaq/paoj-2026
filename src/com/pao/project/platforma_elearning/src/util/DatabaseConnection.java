package com.pao.project.platforma_elearning.src.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        Properties properties = new Properties();
        try (java.io.FileInputStream input = new java.io.FileInputStream("src/com/pao/project/platforma_elearning/resources/db.properties")) {
            if (input == null) {
                System.out.println("Eroare: Nu s-a gasit fisierul db.properties in resurse!");
                return;
            }

            properties.load(input);
            String url = properties.getProperty("db.url");

            this.connection = DriverManager.getConnection(url);

            initializeazaBazaDeDate();

        }

        catch (IOException | SQLException e) {
            System.out.println("Eroare la initializarea bazei de date: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        }

        catch (SQLException e) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initializeazaBazaDeDate() {
        try (java.io.FileInputStream input = new java.io.FileInputStream("src/com/pao/project/platforma_elearning/resources/schema.sql")) {
            if (input == null) {
                System.out.println("Eroare la gasirea fisierul schema.sql");
                return;
            }

            String scriptSql = new String(input.readAllBytes());

            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(scriptSql);
                System.out.println("Tabelele au fost create/resetate cu succes in SQLite!");
            }
        }

        catch (IOException | SQLException e) {
            System.out.println("Eroare la executarea scriptului SQL: " + e.getMessage());
        }
    }
}
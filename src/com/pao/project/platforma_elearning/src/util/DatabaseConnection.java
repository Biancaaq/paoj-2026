package com.pao.project.platforma_elearning.src.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        String caleProperties = "com/pao/project/platforma_elearning/resources/db.properties";
        InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(caleProperties);

        try {
            if (input == null) {
                System.out.println("Eroare: Nu s-a gasit fisierul la calea: " + caleProperties);
                this.connection = DriverManager.getConnection("jdbc:sqlite:platforma_elearning.db");
            }

            else {
                properties.load(input);
                String url = properties.getProperty("db.url");
                this.connection = DriverManager.getConnection(url);
                input.close();
            }

            initializeazaBazaDeDate();
        }

        catch (Exception e) {
            System.out.println("Eroare la initializarea bazei de date: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
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
        String caleSchema = "com/pao/project/platforma_elearning/resources/schema.sql";
        InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(caleSchema);

        if (input == null) {
            System.out.println("Eroare: Nu s-a gasit schema.sql la calea: " + caleSchema);
            return;
        }

        try {
            StringBuilder sb = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String linie;

                while ((linie = reader.readLine()) != null) {
                    sb.append(linie).append("\n");
                }
            }

            String[] comenzi = sb.toString().split(";");

            try (Statement stmt = connection.createStatement()) {
                for (String comanda : comenzi) {
                    String comandaCurata = comanda.trim();
                    if (!comandaCurata.isEmpty()) {
                        stmt.executeUpdate(comandaCurata);
                    }
                }

                System.out.println("Tabelele au fost create/resetate cu succes in SQLite!");
            }
        }

        catch (Exception e) {
            System.out.println("Eroare la executarea scriptului SQL: " + e.getMessage());
        }
    }
}
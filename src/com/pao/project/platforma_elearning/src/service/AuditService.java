package com.pao.project.platforma_elearning.src.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;
    private static final String FISIER_AUDIT = "audit.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private AuditService() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FISIER_AUDIT, true))) {
        }

        catch (IOException e) {
            System.out.println("Eroare la initializarea fisierului de audit: " + e.getMessage());
        }
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }

        return instance;
    }

    public synchronized void logActiune(String numeActiune) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FISIER_AUDIT, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            pw.println(numeActiune + "," + timestamp);
        }

        catch (IOException e) {
            System.out.println("Eroare la scrierea in fisierul de audit: " + e.getMessage());
        }
    }
}
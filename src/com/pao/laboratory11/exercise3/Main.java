package com.pao.laboratory11.exercise3;

import com.pao.laboratory11.exercise1.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Transaction> data = List.of(
                new Transaction(1, new BigDecimal("1200.00"), LocalDate.now(), "RO", "WEB"),
                new Transaction(2, new BigDecimal("500.00"), LocalDate.now(), "RO", "ATM"),
                new Transaction(3, new BigDecimal("3000.00"), LocalDate.now(), "NL", "WEB"),
                new Transaction(4, new BigDecimal("150.00"), LocalDate.now(), "FR", "APP"),
                new Transaction(5, new BigDecimal("3000.00"), LocalDate.now(), "DE", "WEB")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        System.out.println("Demo Snapshot Analitic");

        System.out.println("\n1. Top 3 Tranzactii dupa suma:");
        snap.getTopTransactions().forEach(t -> System.out.println("ID: " + t.getId() + " | Suma: " + t.getAmount()));

        System.out.println("\n2. Volum tranzactii pe tari:");
        snap.getCountByCountry().entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\n3. Statistici generale:");
        System.out.println("Total rulat: " + snap.getTotalAmount());
        System.out.println("Canal WEB utilizat de: " + snap.getCountByChannel().getOrDefault("WEB", 0L) + " ori");
    }
}
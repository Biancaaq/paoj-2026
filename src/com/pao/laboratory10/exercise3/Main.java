package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private String contSursa;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.contSursa = contSursa;
    }

    public int getId() { return id; }
    public double getSuma() { return suma; }
    public String getData() { return data; }
    public TipTranzactie getTip() { return tip; }
    public String getContSursa() { return contSursa; }

    @Override
    public String toString() {
        return String.format("[%d] %s %s: %.2f RON (Cont: %s)", id, data, tip, suma, contSursa);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Tranzactie> tranzactii = Arrays.asList(
                new Tranzactie(1, 1500.0, "2024-01-10", TipTranzactie.CREDIT, "RO01BANC"),
                new Tranzactie(2, 200.0,  "2024-01-15", TipTranzactie.DEBIT,  "RO02INGB"),
                new Tranzactie(3, 500.0,  "2024-02-05", TipTranzactie.CREDIT, "RO01BANC"),
                new Tranzactie(4, 1200.0, "2024-02-18", TipTranzactie.DEBIT,  "RO03BTRA"),
                new Tranzactie(5, 300.0,  "2024-03-01", TipTranzactie.DEBIT,  "RO01BANC"),
                new Tranzactie(6, 450.0,  "2024-03-12", TipTranzactie.CREDIT, "RO02INGB"),
                new Tranzactie(7, 100.0,  "2024-01-20", TipTranzactie.DEBIT,  "RO04REVO"),
                new Tranzactie(8, 2000.0, "2024-02-25", TipTranzactie.CREDIT, "RO03BTRA"),
                new Tranzactie(9, 50.0,   "2024-03-20", TipTranzactie.DEBIT,  "RO04REVO"),
                new Tranzactie(10, 800.0, "2024-01-05", TipTranzactie.CREDIT, "RO02INGB")
        );

        System.out.println("1. Filtrare tip == CREDIT:");
        tranzactii.stream().filter(t -> t.getTip() == TipTranzactie.CREDIT).forEach(System.out::println);

        System.out.println("\n2. Total procesat:");
        double total = tranzactii.stream().mapToDouble(Tranzactie::getSuma).sum();
        System.out.printf("Total procesat: %.2f RON\n", total);

        System.out.println("\n3. Suma per luna:");
        Map<String, Double> perLunaSuma = tranzactii.stream().collect(Collectors.groupingBy(t -> t.getData().substring(0, 7), TreeMap::new, Collectors.summingDouble(Tranzactie::getSuma)));
        perLunaSuma.forEach((luna, suma) -> System.out.printf("Per luna: %s: %.2f RON\n", luna, suma));

        System.out.println("\n4. Top 3 tranzactii (suma descrescatoare):");
        tranzactii.stream().sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed()).limit(3).forEach(System.out::println);

        System.out.println("\n5. Conturi sursa unice:");
        List<String> conturiUnice = tranzactii.stream().map(Tranzactie::getContSursa).distinct().collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturiUnice);

        System.out.println("\n6. Suma medie a tranzactiilor:");
        double medie = tranzactii.stream().mapToDouble(Tranzactie::getSuma).average().orElse(0.0);
        System.out.printf("Suma medie: %.2f RON\n", medie);

        System.out.println("\n7. Extras de cont complet (grupare per luna):");
        Map<String, List<Tranzactie>> extrasGrupat = tranzactii.stream().collect(Collectors.groupingBy(t -> t.getData().substring(0, 7), TreeMap::new, Collectors.toList()));

        extrasGrupat.forEach((luna, lista) -> {
            double sumaLuna = lista.stream().mapToDouble(Tranzactie::getSuma).sum();
            System.out.printf("Extras de cont - %s: %d tranzactii, total: %.2f RON\n", luna, lista.size(), sumaLuna);
            lista.forEach(t -> System.out.println("   " + t));
        });
    }
}
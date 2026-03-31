package com.pao.laboratory06.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        List<Colaborator> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = sc.next();
            Colaborator c = switch (tip) {
                case "CIM" -> new CIMColaborator();
                case "PFA" -> new PFAColaborator();
                case "SRL" -> new SRLColaborator();
                default -> null;
            };
            if (c != null) {
                c.citeste(sc);
                lista.add(c);
            }
        }

        lista.sort((a, b) -> Double.compare(b.calculeazaVenitNetAnual(), a.calculeazaVenitNetAnual()));
        lista.forEach(Colaborator::afiseaza);

        System.out.print("\nColaborator cu venit net maxim: ");
        lista.stream()
                .max(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual))
                .ifPresent(Colaborator::afiseaza);

        System.out.println("\nColaboratori persoane juridice:");
        lista.stream()
                .filter(c -> c instanceof PersoanaJuridica)
                .forEach(Colaborator::afiseaza);

        System.out.println("\nSume și număr colaboratori pe tip:");
        for (String tip : List.of("CIM", "PFA", "SRL")) {
            double suma = 0;
            int count = 0;
            for (Colaborator c : lista) {
                if (c.tipContract().equals(tip)) {
                    suma += c.calculeazaVenitNetAnual();
                    count++;
                }
            }
            if (count > 0) {
                System.out.printf("%s: suma = %.2f lei, număr = %d\n", tip, suma, count);
            }
        }
    }
}
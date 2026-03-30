package com.pao.laboratory06.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if(!sc.hasNextInt()) return;
        int n = sc.nextInt();
        List<Colaborator> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!sc.hasNext()) break;
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
        for (TipColaborator t : TipColaborator.values()) {
            double suma = 0;
            int count = 0;
            for(Colaborator c : lista) {
                if(c.tipContract().equals(t.name())) {
                    suma += c.calculeazaVenitNetAnual();
                    count++;
                }
            }
            if (count > 0) {
                System.out.printf("%s: suma = %.2f lei, număr = %d\n", t.name(), suma, count);
            }
        }
    }
}
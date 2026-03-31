package com.pao.laboratory06.exercise3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Inginer i1 = new Inginer("Ionescu", "Dan", "0722", 6000);
        Inginer i2 = new Inginer("Popescu", "Ana", "0733", 8500);
        Inginer i3 = new Inginer("Andrei", "Vasile", "0744", 4000);

        List<Inginer> lista = new ArrayList<>(Arrays.asList(i1, i2, i3));

        lista.sort(null);
        System.out.println("Sortare dupa nume:");
        lista.forEach(System.out::println);

        lista.sort(new ComparatorInginerSalariu());
        System.out.println("\nSortare dupa salariu descrescator:");
        lista.forEach(System.out::println);

        System.out.println("\nAcces prin PlataOnline (referinta tip interfata):");
        PlataOnline plata = i2;
        plata.autentificare("admin", "1234");
        System.out.println("Sold: " + plata.consultareSold());

        System.out.println("\nDemonstratie SMS:");
        PersoanaJuridica pj = new PersoanaJuridica("Soft SRL", "021333");
        PersoanaJuridica pjFaraTel = new PersoanaJuridica("Firma Noua", null);

        System.out.println("Trimitere PJ valida: " + pj.trimiteSMS("Mesaj test"));
        System.out.println("Trimitere PJ fara tel: " + pjFaraTel.trimiteSMS("Mesaj test"));

        System.out.println("\nConstanta financiara:");
        System.out.println("TVA: " + ConstanteFinanciare.TVA.getValoare());

        System.out.println("\nTratare erori:");
        try {
            i1.autentificare(null, "");
        } catch (IllegalArgumentException e) {
            System.out.println("Capturat IllegalArgumentException");
        }

        try {
            PlataOnline testSms = i1;
            if (!(testSms instanceof PlataOnlineSMS)) {
                throw new UnsupportedOperationException("Nu suporta SMS");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Capturat: " + e.getMessage());
        }
    }
}
package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

        List<Student> studenti = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;

            while ((linie = br.readLine()) != null) {
                String[] date = linie.split(",");

                if (date.length == 4) {
                    String nume = date[0].trim();
                    int varsta = Integer.parseInt(date[1].trim());
                    String oras = date[2].trim();
                    String strada = date[3].trim();

                    studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
                }
            }
        }

        catch (FileNotFoundException e) {
            System.err.println("Fisierul nu a fost gasit");

            return;
        }

        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNext()) {
            return;
        }

        String input = scanner.nextLine();
        String[] partiComanda = input.split(" ", 2);
        String comanda = partiComanda[0].toUpperCase();

        switch (comanda) {
            case "PRINT":
                for (Student s : studenti) {
                    System.out.println(s);
                }

                break;

            case "SHALLOW":
            case "DEEP":
                if (partiComanda.length < 2) {
                    break;
                }

                String numeCautat = partiComanda[1];

                for (Student original : studenti) {
                    if (original.getNume().equalsIgnoreCase(numeCautat)) {
                        Student clona = comanda.equals("SHALLOW") ? original.shallowClone() : original.deepClone();

                        clona.getAdresa().setOras("MODIFICAT");

                        System.out.println("Original: " + original);
                        System.out.println("Clona: " + clona);

                        break;
                    }
                }

                break;
        }

        scanner.close();
    }
}

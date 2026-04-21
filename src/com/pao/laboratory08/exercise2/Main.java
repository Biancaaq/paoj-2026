package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String OUT_FILE = "src/com/pao/laboratory08/exercise2/rezultate.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă

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

        catch (IOException e) {
            System.err.println("Eroare: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int prag = scanner.nextInt();
        scanner.close();

        List<Student> studentiFiltrati = new ArrayList<>();

        for (Student s : studenti) {
            if (s.getVarsta() >= prag) {
                studentiFiltrati.add(s);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUT_FILE))) {
            for (Student s : studentiFiltrati) {
                bw.write(s.toString());
                bw.newLine();
            }
        }

        catch (IOException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + studentiFiltrati.size() + " studenti\n");

        for (Student s : studentiFiltrati) {
            System.out.println(s);
        }
    }
}


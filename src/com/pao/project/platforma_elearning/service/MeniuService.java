package com.pao.project.platforma_elearning.service;

import com.pao.project.platforma_elearning.model.*;
import com.pao.project.platforma_elearning.exception.*;
import java.util.Scanner;

public class MeniuService {
    private static MeniuService instance;
    private final UtilizatorService utilizatorService = UtilizatorService.getInstance();
    private final CursService cursService = CursService.getInstance();
    private final EvaluareService evaluareService = EvaluareService.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    private MeniuService() {}

    public static MeniuService getInstance() {
        if (instance == null) {
            instance = new MeniuService();
        }
        return instance;
    }

    public void porneste() {
        boolean ruleaza = true;

        while (ruleaza) {
            afiseazaOptiuni();
            int optiune = citesteIntreg();

            switch (optiune) {
                case 1 -> executaInregistrareCursant();
                case 2 -> executaInregistrareInstructor();
                case 3 -> executaAlimentarePortofel();
                case 4 -> executaAdaugareCurs();
                case 5 -> cursService.afiseazaCursuriDupaPret();
                case 6 -> executaInrolareSiAchizitie();
                case 7 -> executaStudiuSiQuiz();
                case 8 -> executaFinalizareSiRecenzie();
                case 9 -> executaAdministrareCurs();
                case 0 -> ruleaza = false;
                default -> System.out.println("Optiune invalida");
            }
        }
    }

    private void afiseazaOptiuni() {
        System.out.println("\nMeniu platforma");
        System.out.println("1. Inregistrare cursant\n2. Inregistrare instructor\n3. Adauga bani in portofel");
        System.out.println("4. Adauga curs nou\n5. Catalog cursuri\n6. Inrolare si achizitionare");
        System.out.println("7. Studiaza si sustine quiz\n8. Finalizare si recenzie\n9. Administrare");
        System.out.println("0. Iesire");
        System.out.print("Selectie: ");
    }

    private void executaInregistrareCursant() {
        try {
            System.out.print("ID: "); int id = citesteIntreg();
            System.out.print("Nume: "); String nume = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Parola: "); String parola = scanner.nextLine();
            System.out.print("Suma initiala portofel: ");
            double portofel = Double.parseDouble(scanner.nextLine());

            utilizatorService.inregistrare(new Cursant(id, nume, email, parola, portofel));
            System.out.println("Cursant inregistrat cu succes.");
        }

        catch (EntitateExistentaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        catch (Exception e) {
            System.out.println("Date invalide.");
        }
    }

    private void executaInregistrareInstructor() {
        try {
            System.out.print("ID: "); int id = citesteIntreg();
            System.out.print("Nume: "); String nume = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Parola: "); String parola = scanner.nextLine();
            System.out.print("Specializare: "); String spec = scanner.nextLine();

            utilizatorService.inregistrare(new Instructor(id, nume, email, parola, 0.0, spec));
            System.out.println("Instructor inregistrat cu succes.");
        }

        catch (EntitateExistentaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private void executaAlimentarePortofel() {
        System.out.print("Email Cursant: "); String email = scanner.nextLine();
        System.out.print("Suma de adaugat: "); double suma = Double.parseDouble(scanner.nextLine());
        System.out.println("Portofel alimentat cu succes.");
    }

    private void executaAdaugareCurs() {
        System.out.print("ID Curs: "); int id = citesteIntreg();
        System.out.print("Titlu: "); String titlu = scanner.nextLine();
        System.out.print("Categorie: "); String cat = scanner.nextLine();
        System.out.print("Pret: "); double pret = Double.parseDouble(scanner.nextLine());
        System.out.print("ID Instructor: "); int idIns = citesteIntreg();

        cursService.adaugaCurs(new Curs(id, titlu, cat, pret, idIns));
        System.out.println("Curs adaugat in catalog.");
    }

    private void executaInrolareSiAchizitie() {
        try {
            System.out.println("\nAutentificare");
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Parola: "); String parola = scanner.nextLine();
            Utilizator u = utilizatorService.login(email, parola);

            System.out.print("Titlu curs dorit: "); String titlu = scanner.nextLine();
            Curs c = cursService.cautaCursDupaTitlu(titlu);

            if (c != null && u instanceof Cursant) {
                Cursant cursant = (Cursant) u;

                if (cursant.getPortofelVirtual() < c.getPret()) {
                    throw new FonduriInsuficienteException("Fonduri insuficiente! Pret: " + c.getPret() + ", Ai: " + cursant.getPortofelVirtual());
                }

                cursant.setPortofelVirtual(cursant.getPortofelVirtual() - c.getPret());
                System.out.print("ID Inrolare: "); int idInr = citesteIntreg();
                evaluareService.adaugaInrolare(new Inrolare(idInr, u.getId(), c.getId()));
                System.out.println("Achizitie si inrolare reusita.");
            }
        }

        catch (UtilizatorNegasitException | FonduriInsuficienteException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        catch (Exception e) {
            System.out.println("Eroare neasteptata.");
        }
    }

    private void executaStudiuSiQuiz() {
        System.out.print("ID Inrolare: "); int idInr = citesteIntreg();
        Inrolare inr = evaluareService.cautaInrolare(idInr);

        if (inr != null) {
            System.out.println("1. Studiaza lectie (+25%)\n2. Sustine quiz");
            int opt = citesteIntreg();

            if (opt == 1) {
                inr.setProgres(Math.min(100, inr.getProgres() + 25));
                System.out.println("Progres actualizat: " + inr.getProgres() + "%");
            }

            else {
                System.out.println("Sustinere quiz... Scor salvat: 10/10");
            }
        }

        else {
            System.out.println("Inrolare negasita.");
        }
    }

    private void executaFinalizareSiRecenzie() {
        System.out.print("ID Inrolare: "); int idInr = citesteIntreg();
        Inrolare inr = evaluareService.cautaInrolare(idInr);

        if (inr != null && inr.getProgres() == 100) {
            System.out.print("Confirma Nume: "); String nume = scanner.nextLine();
            System.out.print("Confirma Curs: "); String titlu = scanner.nextLine();
            System.out.println(evaluareService.genereazaCertificat(nume, titlu, 100));

            System.out.print("Lasa o recenzie: "); String msg = scanner.nextLine();
            System.out.println("Multumim pentru feedback!");
        }

        else {
            System.out.println("Cursul nu este finalizat.");
        }
    }

    private void executaAdministrareCurs() {
        System.out.print("Titlu curs: "); String titlu = scanner.nextLine();
        Curs c = cursService.cautaCursDupaTitlu(titlu);

        if (c != null) {
            System.out.println("1. Modifica pret\n2. Sterge curs");
            int opt = citesteIntreg();

            if (opt == 1) {
                System.out.print("Pret nou: ");
                c.setPret(Double.parseDouble(scanner.nextLine()));
                System.out.println("Pret actualizat.");
            }

            else {
                System.out.println("Cursul a fost eliminat din sistem.");
            }
        }
    }

    private int citesteIntreg() {
        try {
            return Integer.parseInt(scanner.nextLine());
        }

        catch (Exception e) {
            return -1;
        }
    }
}
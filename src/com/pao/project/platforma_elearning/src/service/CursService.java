package com.pao.project.platforma_elearning.src.service;

import com.pao.project.platforma_elearning.src.exception.EntitateExistentaException;
import com.pao.project.platforma_elearning.src.model.Curs;
import com.pao.project.platforma_elearning.src.repository.CursRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CursService {
    private static CursService instance;
    private final CursRepository cursRepository = new CursRepository();

    private List<Curs> cursuriMemorie = null;

    private CursService() {}

    public static CursService getInstance() {
        if (instance == null) {
            instance = new CursService();
        }

        return instance;
    }

    private void sincronizeaza() {
        if (cursuriMemorie == null) {
            cursuriMemorie = cursRepository.findAll();
        }
    }

    public void adaugaCurs(Curs c) throws EntitateExistentaException {
        sincronizeaza();

        if (cautaCursDupaTitlu(c.getTitlu()) != null) {
            throw new EntitateExistentaException("Exista deja un curs cu acest titlu in platforma");
        }

        cursRepository.save(c);
        cursuriMemorie.add(c);
    }

    public void afiseazaCursuriDupaPret() {
        sincronizeaza();

        if (cursuriMemorie.isEmpty()) {
            System.out.println("Nu exista cursuri in platforma momentan");
            return;
        }

        List<Curs> cursuriSortate = new ArrayList<>(cursuriMemorie);
        Collections.sort(cursuriSortate);

        for (Curs c : cursuriSortate) {
            System.out.println(c);
        }
    }

    public void afiseazaCursuriDupaCategorie(String categorie) {
        sincronizeaza();
        boolean gasit = false;

        for (Curs c : cursuriMemorie) {
            if (c.getCategorie().equalsIgnoreCase(categorie)) {
                System.out.println(c);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Nu am gasit niciun curs in categoria: " + categorie);
        }
    }

    public void afiseazaCursuriInstructor(int idInstructor) {
        sincronizeaza();
        System.out.println("\nCursurile mele:");
        boolean gasit = false;

        for (Curs c : cursuriMemorie) {
            if (c.getIdInstructor() == idInstructor) {
                System.out.println(c);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Nu ai creat niciun curs inca");
        }
    }

    public Curs cautaCursDupaTitlu(String titlu) {
        sincronizeaza();

        return cursuriMemorie.stream().filter(c -> c.getTitlu().equalsIgnoreCase(titlu)).findFirst().orElse(null);
    }

    public Curs cautaCursDupaId(int id) {
        sincronizeaza();

        return cursuriMemorie.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Curs stergeCurs(String titlu) {
        sincronizeaza();
        Curs c = cautaCursDupaTitlu(titlu);

        if (c != null) {
            cursRepository.delete(c.getId());
            cursuriMemorie.remove(c);
            System.out.println("Cursul a fost eliminat din baza de date");
            return c;
        }

        System.out.println("Cursul nu a fost gasit");
        return null;
    }

    public void modificaPretCurs(String titlu, double pretNou) {
        sincronizeaza();
        Curs c = cautaCursDupaTitlu(titlu);

        if (c != null) {
            c.setPret(pretNou);
            cursRepository.update(c);
            System.out.println("Pret actualizat cu succes in DB pentru cursul: " + titlu);
        }

        else {
            System.out.println("Cursul nu a fost gasit pentru actualizarea pretului");
        }
    }
}
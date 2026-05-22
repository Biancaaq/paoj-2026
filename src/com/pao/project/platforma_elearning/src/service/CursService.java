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

    private CursService() {}

    public static CursService getInstance() {
        if (instance == null) {
            instance = new CursService();
        }

        return instance;
    }

    public void adaugaCurs(Curs c) throws EntitateExistentaException {
        if (cautaCursDupaTitlu(c.getTitlu()) != null) {
            throw new EntitateExistentaException("Exista deja un curs cu acest titlu in platforma");
        }

        cursRepository.save(c);
    }

    public void afiseazaCursuriDupaPret() {
        List<Curs> cursuri = cursRepository.findAll();

        if (cursuri.isEmpty()) {
            System.out.println("Nu exista cursuri in platforma momentan");
            return;
        }

        Collections.sort(cursuri);
        cursuri.forEach(System.out::println);
    }

    public void afiseazaCursuriDupaCategorie(String categorie) {
        List<Curs> cursuri = cursRepository.findAll();
        boolean gasit = false;

        for (Curs c : cursuri) {
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
        cursRepository.afiseazaCursuriCuInstructori();
    }

    public Curs cautaCursDupaTitlu(String titlu) {
        return cursRepository.findAll().stream().filter(c -> c.getTitlu().equalsIgnoreCase(titlu)).findFirst().orElse(null);
    }

    public Curs cautaCursDupaId(int id) {
        return cursRepository.findById(id).orElse(null);
    }

    public Curs stergeCurs(String titlu) {
        Curs c = cautaCursDupaTitlu(titlu);

        if (c != null) {
            cursRepository.delete(c.getId());
            System.out.println("Cursul a fost eliminat din baza de date");
            return c;
        }

        System.out.println("Cursul nu a fost gasit");
        return null;
    }
}
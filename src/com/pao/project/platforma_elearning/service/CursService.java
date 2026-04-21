package com.pao.project.platforma_elearning.service;

import com.pao.project.platforma_elearning.model.Curs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CursService {
    private static CursService instance;
    private List<Curs> cursuri = new ArrayList<>();

    private CursService() {}

    public static CursService getInstance() {
        if (instance == null) {
            instance = new CursService();
        }

        return instance;
    }

    public void adaugaCurs(Curs c) {
        cursuri.add(c);
    }

    public void afiseazaCursuriDupaPret() {
        cursuri.stream().sorted(Comparator.comparingDouble(Curs::getPret)).forEach(System.out::println);
    }

    public Curs cautaCursDupaTitlu(String titlu) {
        return cursuri.stream().filter(c -> c.getTitlu().equalsIgnoreCase(titlu)).findFirst().orElse(null);
    }
}
package com.pao.project.platforma_elearning.service;

import com.pao.project.platforma_elearning.model.Certificat;
import com.pao.project.platforma_elearning.model.Inrolare;
import com.pao.project.platforma_elearning.model.ScorQuiz;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EvaluareService {
    private static EvaluareService instance;
    private List<Inrolare> inrolari = new ArrayList<>();
    private List<ScorQuiz> scoruri = new ArrayList<>();

    private EvaluareService() {}

    public static EvaluareService getInstance() {
        if (instance == null) {
            instance = new EvaluareService();
        }

        return instance;
    }

    public void adaugaInrolare(Inrolare i) {
        inrolari.add(i);
        System.out.println("Inrolare inregistrata: " + i);
    }

    public void salveazaScor(ScorQuiz s) {
        scoruri.add(s);
        System.out.println("Scor nou salvat: " + s.getPunctaj() + " puncte");
    }

    public Certificat genereazaCertificat(String numeCursant, String numeCurs, double progres) {
        if (progres >= 100.0) {
            String codUnic = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Certificat c = new Certificat(codUnic, numeCursant, numeCurs);
            System.out.println("Felicitari! Cod certificat: " + codUnic);

            return c;
        }

        System.out.println("Certificatul este primit doar dupa finalizarea cursului. Progres curent: " + progres + "%");

        return null;
    }

    public Inrolare cautaInrolare(int idInrolare) {
        for (Inrolare i : inrolari) {
            if (i.getIdInrolare() == idInrolare) {
                return i;
            }
        }

        return null;
    }

    public void afiseazaToateInrolarile() {
        inrolari.forEach(System.out::println);
    }
}
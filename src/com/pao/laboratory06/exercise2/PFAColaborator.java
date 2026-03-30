package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private double cheltuieli;
    private final double SALARIU_MINIM_BRUT = 4050.0;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieli = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieli) * 12;
        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 6 * SALARIU_MINIM_BRUT) {
            cass = 0.10 * (6 * SALARIU_MINIM_BRUT);
        } else if (venitNet <= 72 * SALARIU_MINIM_BRUT) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * SALARIU_MINIM_BRUT);
        }

        double cas = 0;
        if (venitNet >= 12 * SALARIU_MINIM_BRUT && venitNet <= 24 * SALARIU_MINIM_BRUT) {
            cas = 0.25 * (12 * SALARIU_MINIM_BRUT);
        } else if (venitNet > 24 * SALARIU_MINIM_BRUT) {
            cas = 0.25 * (24 * SALARIU_MINIM_BRUT);
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public String tipContract() { return "PFA"; }
}
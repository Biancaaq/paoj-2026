package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private double cheltuieli;
    private final double SAL_MINIM_ANUAL = 4050.0 * 12;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitBrutLunar = in.nextDouble();
        cheltuieli = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieli) * 12;
        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 0.5 * SAL_MINIM_ANUAL) cass = 0.10 * (0.5 * SAL_MINIM_ANUAL);
        else if (venitNet <= 6 * SAL_MINIM_ANUAL) cass = 0.10 * venitNet;
        else cass = 0.10 * (6 * SAL_MINIM_ANUAL);

        double cas = 0;
        if (venitNet >= SAL_MINIM_ANUAL && venitNet <= 2 * SAL_MINIM_ANUAL) cas = 0.25 * SAL_MINIM_ANUAL;
        else if (venitNet > 2 * SAL_MINIM_ANUAL) cas = 0.25 * (2 * SAL_MINIM_ANUAL);

        return venitNet - impozit - cass - cas;
    }

    @Override
    public String tipContract() { return "PFA"; }
}
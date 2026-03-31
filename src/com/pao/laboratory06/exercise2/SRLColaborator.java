package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica {
    private double cheltuieli;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitBrutLunar = in.nextDouble();
        cheltuieli = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieli) * 12 * 0.84;
    }

    @Override
    public String tipContract() { return "SRL"; }
}
package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica {
    private double cheltuieli;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieli = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieli) * 12 * 0.84;
    }

    @Override
    public String tipContract() { return "SRL"; }
}
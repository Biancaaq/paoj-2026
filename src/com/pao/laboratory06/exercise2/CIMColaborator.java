package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus = false;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitBrutLunar = in.nextDouble();
        if (in.hasNext("DA") || in.hasNext("NU")) {
            bonus = in.next().equals("DA");
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double netAnual = venitBrutLunar * 12 * 0.55;
        return bonus ? netAnual * 1.10 : netAnual;
    }

    @Override
    public String tipContract() { return "CIM"; }
    @Override
    public boolean areBonus() { return bonus; }
}
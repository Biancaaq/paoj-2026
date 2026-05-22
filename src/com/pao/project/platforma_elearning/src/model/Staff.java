package com.pao.project.platforma_elearning.src.model;

public abstract class Staff extends Utilizator {
    protected double salariu;

    public Staff(String nume, String email, String parola, double salariu) {
        super(nume, email, parola);
        this.salariu = salariu;
    }

    public double getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return super.toString() + ", salariu = " + salariu;
    }
}
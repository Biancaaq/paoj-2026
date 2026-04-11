package com.pao.project.platforma_elearning.model;

public abstract class Staff extends Utilizator {
    protected double salariu;

    public Staff(int id, String nume, String email, String parola, double salariu) {
        super(id, nume, email, parola);
        this.salariu = salariu;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return super.toString() + ", salariu = " + salariu;
    }
}
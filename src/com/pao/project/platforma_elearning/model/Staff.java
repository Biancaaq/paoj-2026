package com.pao.project.platforma_elearning.model;

public abstract class Staff extends Utilizator {
    protected double salariu;

    public Staff(int id, String nume, String email, String parola, double salariu) {
        super(id, nume, email, parola);
        this.salariu = salariu;
    }
}
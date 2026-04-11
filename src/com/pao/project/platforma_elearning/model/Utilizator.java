package com.pao.project.platforma_elearning.model;

public abstract class Utilizator {
    protected int id;
    protected String nume;
    protected String email;
    protected String parola;

    public Utilizator(int id, String nume, String email, String parola) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }
}
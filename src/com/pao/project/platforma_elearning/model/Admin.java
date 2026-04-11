package com.pao.project.platforma_elearning.model;

public class Admin extends Staff {
    private int nivelAcces;

    public Admin(int id, String nume, String email, String parola, double salariu, int nivelAcces) {
        super(id, nume, email, parola, salariu);
        this.nivelAcces = nivelAcces;
    }
}
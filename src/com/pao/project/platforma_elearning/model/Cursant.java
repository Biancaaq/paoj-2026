package com.pao.project.platforma_elearning.model;

public class Cursant extends Utilizator {
    private double portofelVirtual;

    public Cursant(int id, String nume, String email, String parola, double portofelVirtual) {
        super(id, nume, email, parola);
        this.portofelVirtual = portofelVirtual;
    }
}
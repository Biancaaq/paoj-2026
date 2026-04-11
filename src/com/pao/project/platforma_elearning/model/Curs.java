package com.pao.project.platforma_elearning.model;

public class Curs {
    private int id;
    private String titlu;
    private String categorie;
    private double pret;
    private int idInstructor;

    public Curs(int id, String titlu, String categorie, double pret, int idInstructor) {
        this.id = id;
        this.titlu = titlu;
        this.categorie = categorie;
        this.pret = pret;
        this.idInstructor = idInstructor;
    }
}
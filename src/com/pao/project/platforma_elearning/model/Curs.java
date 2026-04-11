package com.pao.project.platforma_elearning.model;

import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) { this.categorie = categorie; }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) { this.pret = pret; }

    public int getIdInstructor() { return idInstructor; }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }

        if (!(ob instanceof Curs)) {
            return false;
        }

        Curs curs = (Curs) ob;

        return id == curs.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Curs: id = " + id + ", titlu = " + titlu + ", categorie = " + categorie + ", pret = " + pret;
    }
}
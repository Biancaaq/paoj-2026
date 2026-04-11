package com.pao.project.platforma_elearning.model;

public class Instructor extends Staff {
    private String specializare;
    private double venituriIncasate;

    public Instructor(int id, String nume, String email, String parola, double salariu, String specializare) {
        super(id, nume, email, parola, salariu);
        this.specializare = specializare;
        this.venituriIncasate = 0.0;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public double getVenituriIncasate() {
        return venituriIncasate;
    }

    public void setVenituriIncasate(double venituriIncasate) {
        this.venituriIncasate = venituriIncasate;
    }

    @Override
    public String toString() {
        return "Instructor: " + super.toString() + ", specializare = " + specializare + ", venituri = " + venituriIncasate;
    }
}
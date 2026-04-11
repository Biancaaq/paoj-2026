package com.pao.project.platforma_elearning.model;

public class Quiz {
    private int id;
    private String titlu;
    private int nrIntrebari;
    private double pragTrecere;

    public Quiz(int id, String titlu, int nrIntrebari, double pragTrecere) {
        this.id = id;
        this.titlu = titlu;
        this.nrIntrebari = nrIntrebari;
        this.pragTrecere = pragTrecere;
    }
}
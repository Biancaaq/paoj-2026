package com.pao.project.platforma_elearning.model;

public class Lectie {
    private String titlu;
    private int durataMinute;
    private String continut;

    public Lectie(String titlu, int durataMinute, String continut) {
        this.titlu = titlu;
        this.durataMinute = durataMinute;
        this.continut = continut;
    }

    public String getTitlu() { return titlu; }

    public void setTitlu(String titlu) { this.titlu = titlu; }

    public int getDurataMinute() { return durataMinute; }

    public void setDurataMinute(int durataMinute) { this.durataMinute = durataMinute; }

    public String getContinut() { return continut; }

    public void setContinut(String continut) { this.continut = continut; }

    @Override
    public String toString() {
        return "Lectie: titlu = " + titlu + ", durata = " + durataMinute + "min";
    }
}
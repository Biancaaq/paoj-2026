package com.pao.project.platforma_elearning.model;

import java.time.LocalDate;

public class Inrolare {
    private int idInrolare;
    private int idCursant;
    private int idCurs;
    private LocalDate dataInrolarii;
    private double progres;

    public Inrolare(int idInrolare, int idCursant, int idCurs) {
        this.idInrolare = idInrolare;
        this.idCursant = idCursant;
        this.idCurs = idCurs;
        this.dataInrolarii = LocalDate.now();
        this.progres = 0.0;
    }

    public int getIdInrolare() {
        return idInrolare;
    }

    public int getIdCursant() {
        return idCursant;
    }

    public int getIdCurs() {
        return idCurs;
    }

    public LocalDate getDataInrolarii() { return dataInrolarii; }

    public double getProgres() {
        return progres;
    }

    public void setProgres(double progress) {
        this.progres = progress;
    }

    @Override
    public String toString() {
        return "Inrolare: id = " + idInrolare + ", id cursant = " + idCursant + ", id curs = " + idCurs + ", progres = " + progres + "%";
    }
}
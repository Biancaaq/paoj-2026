package com.pao.project.platforma_elearning.model;

import java.time.LocalDate;

public class Inrolare {
    private int idInrolare;
    private int idCursant;
    private int idCurs;
    private LocalDate dataInrolarii;
    private double progress;

    public Inrolare(int idInrolare, int idCursant, int idCurs) {
        this.idInrolare = idInrolare;
        this.idCursant = idCursant;
        this.idCurs = idCurs;
        this.dataInrolarii = LocalDate.now();
        this.progress = 0.0;
    }
}
package com.pao.project.platforma_elearning.model;

import java.time.LocalDateTime;

public class ScorQuiz {
    private int idCursant;
    private int idQuiz;
    private double punctaj;
    private LocalDateTime dataSustinerii;

    public ScorQuiz(int idCursant, int idQuiz, double punctaj) {
        this.idCursant = idCursant;
        this.idQuiz = idQuiz;
        this.punctaj = punctaj;
        this.dataSustinerii = LocalDateTime.now();
    }
}
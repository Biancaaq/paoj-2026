package com.pao.project.platforma_elearning.src;

import com.pao.project.platforma_elearning.src.service.MeniuService;

public class Main {
    public static void main(String[] args) {
        MeniuService meniu = MeniuService.getInstance();
        meniu.porneste();
    }
}
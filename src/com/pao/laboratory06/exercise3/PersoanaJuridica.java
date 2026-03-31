package com.pao.laboratory06.exercise3;

import java.util.*;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise = new ArrayList<>();
    private double sold = 10000;

    public PersoanaJuridica(String nume, String telefon) {
        super(nume, "", telefon);
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || parola == null) throw new IllegalArgumentException();
    }

    @Override
    public double consultareSold() { return sold; }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma > sold) return false;
        sold -= suma;
        return true;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isEmpty()) return false;
        if (this.telefon == null || this.telefon.isEmpty()) return false;
        smsTrimise.add(mesaj);
        return true;
    }
}
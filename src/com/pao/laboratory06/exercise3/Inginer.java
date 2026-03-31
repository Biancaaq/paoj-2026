package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double sold = 0;

    public Inginer(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon, salariu);
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isEmpty() || parola == null || parola.isEmpty()) {
            throw new IllegalArgumentException("Date autentificare invalide!");
        }
        System.out.println("Inginer " + nume + " autentificat.");
    }

    @Override
    public double consultareSold() { return sold; }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) return false;
        sold += suma;
        return true;
    }

    @Override
    public int compareTo(Inginer o) {
        return this.nume.compareTo(o.nume);
    }

    @Override
    public String toString() {
        return "Inginer: " + nume + " | Salariu: " + salariu;
    }
}
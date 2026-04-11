package com.pao.project.platforma_elearning.model;

import java.util.Objects;

public abstract class Utilizator {
    protected int id;
    protected String nume;
    protected String email;
    protected String parola;

    public Utilizator(int id, String nume, String email, String parola) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }

        if (!(ob instanceof  Utilizator)) {
            return false;
        }

        Utilizator u = (Utilizator) ob;

        return id == u.id && Objects.equals(email, u.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "id = " + id + ", nume = " + nume + ", email = " + email;
    }
}
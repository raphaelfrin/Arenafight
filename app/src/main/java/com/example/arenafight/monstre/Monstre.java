package com.example.arenafight.monstre;

import java.io.Serializable;

public class Monstre implements Serializable {

    private String nom;
    private int niveauMin;
    private int niveauMax;
    private int pvMax;
    private int pv;
    private int atq;
    private int def;
    private int imageResId;

    public Monstre(String nom, int niveauMin, int niveauMax, int pv, int pvMax, int atq, int def, int imageResId) {

        this.nom = nom;
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
        this.pvMax = pvMax;
        this.pv = pv;
        this.atq = atq;
        this.def = def;
        this.imageResId = imageResId;
    }

    public String getNom() {
        return nom;
    }
    public int getNiveauMin() {
        return niveauMin;
    }
    public int getNiveauMax() {
        return niveauMax;
    }
    public int getPvMax() {
        return pvMax;
    }
    public int getPv() {
        return pv;
    }
    public int getAtq() {
        return atq;
    }
    public int getDef() {
        return def;
    }

    public void setPv(int pv) { this.pv = pv; }

    public boolean estVivant() {
        return this.pv > 0;
    }
    public int getImageResId() {
        return imageResId;
    }
}

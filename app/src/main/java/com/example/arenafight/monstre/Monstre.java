package com.example.arenafight.monstre;

public class Monstre {

    private String nom;
    private int niveauMin;
    private int niveauMax;
    private int pv;
    private int atq;
    private int def;
    private int imageResId;

    public Monstre(String nom, int niveauMin, int niveauMax, int pv, int atq, int def, int imageResId) {

        this.nom = nom;
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
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
    public int getPv() {
        return pv;
    }
    public int getAtq() {
        return atq;
    }
    public int getDef() {
        return def;
    }
    public int getImageResId() {
        return imageResId;
    }
}

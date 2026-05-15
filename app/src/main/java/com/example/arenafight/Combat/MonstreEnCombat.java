/*package com.example.arenafight.Combat;

import com.example.arenafight.monstre.Monstre;

import java.io.Serializable;

public class MonstreEnCombat implements Serializable {

    private String nom;
    private int pv;
    private int atq;
    private int def;
    private int imageResId;

    public MonstreEnCombat(Monstre monstre) {
        this.nom = monstre.getNom();
        this.pv = monstre.getPv();
        this.atq = monstre.getAtq();
        this.def = monstre.getDef();
        this.imageResId = monstre.getImageResId();
    }

    public String getNom() {
        return nom;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = Math.max(0, pv);
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

    public boolean estVivant() {
        return pv > 0;
    }
}
*/
package com.example.arenafight.Combat;

public class Attaque {
    private String nom;
    private int porteeMin;
    private int porteeMax;
    private double bonusDegats;

    public Attaque(String nom, int porteeMin, int porteeMax, double bonusDegats) {
        this.nom = nom;
        this.porteeMin = porteeMin;
        this.porteeMax = porteeMax;
        this.bonusDegats = bonusDegats;
    }

    // Getters
    public String getNom() { return nom; }
    public int getPorteeMin() { return porteeMin; }
    public int getPorteeMax() { return porteeMax; }
    public double getBonusDegats() { return bonusDegats; }
}
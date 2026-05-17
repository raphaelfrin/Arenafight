package com.example.arenafight.Combat;

import com.example.arenafight.monstre.Monstre;

import java.io.Serializable;

public class CombatState implements Serializable {
    public Monstre monstre;
    public int posJ;
    public int posM;
    public int hpJ;
    public int hpJMax;
    public boolean joueurDefense;
    public boolean enModeDeplacement = false;
    public int distanceDeplacement = 0;
    public boolean tourJoueur = true;
    public boolean combatLance = true;
}

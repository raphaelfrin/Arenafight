package com.example.arenafight.Combat;

import com.example.arenafight.monstre.Monstre;

import java.io.Serializable;

public class CombatState implements Serializable {
    public Monstre monstre;
    public int posJ;
    public int posM;
    public int hpJ;
    public boolean combatLance;
}

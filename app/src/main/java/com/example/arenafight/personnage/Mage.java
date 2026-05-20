package com.example.arenafight.personnage;

import com.example.arenafight.R;

public class Mage extends Personnage {

    public Mage(String nom) {
        super(nom, "Mage",
                10,   // PV max
                2,   // ATQ
                1);   // DEF
        this.imageResId = R.drawable.mage;
    }
}
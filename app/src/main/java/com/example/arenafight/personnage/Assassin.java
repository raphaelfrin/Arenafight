package com.example.arenafight.personnage;

import com.example.arenafight.R;

public class Assassin extends Personnage {
    public Assassin(String nom) {
        super(nom, "Assassin",
                6,   // PV max
                3,   // ATQ
                1);   // DEF );
        this.imageResId = R.drawable.assassin;
    }
}

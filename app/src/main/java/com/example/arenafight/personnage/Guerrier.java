package com.example.arenafight.personnage;

import com.example.arenafight.R;

public class Guerrier extends Personnage {

    public Guerrier(String nom) {
        super(nom, "Guerrier",
                15,   // PV max
                3,   // ATQ
                0);   // DEF );
        this.imageResId = R.drawable.guerrier;
    }
}

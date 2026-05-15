package com.example.arenafight.Combat;

import android.content.Context;

import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.monstre.UtilisMonstre;
import com.example.arenafight.personnage.Personnage;
import com.example.arenafight.databinding.ActivityMainBinding;

public class Combat {
    int taillPlateau = 100;
    int posJ = 0;
    int poseM = taillPlateau - 1;
    private static void lancerCombat(Context context, Personnage perso, ActivityCombatBinding binding) throws Exception {
        MonstreEnCombat monstre = new MonstreEnCombat(
                UtilisMonstre.tirerMonstre(context, perso.getLv())
        );
        afficherPlateau
    }
}
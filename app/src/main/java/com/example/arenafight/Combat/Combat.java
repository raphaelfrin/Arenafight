package com.example.arenafight.Combat;

import android.content.Context;

import android.view.View;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.monstre.UtilisMonstre;
import com.example.arenafight.personnage.Personnage;
import com.example.arenafight.databinding.ActivityMainBinding;

public class Combat {
    int taillPlateau = 10;
    int posJ = 0;
    int posM = taillPlateau - 1;
    public void lancerCombat(Context context, Personnage perso, ActivityCombatBinding binding, CombatState state) throws Exception {

        // créer le monstre seulement si pas encore créé
        if (state.monstre == null) {
            state.monstre = UtilisMonstre.tirerMonstre(context, perso.getLv());

            state.posJ = 0;
            state.posM = taillPlateau - 1;
            state.hpJ = perso.getPv();

            state.combatLance = true;
        }

        Plateau.afficherPlateau(
                context,
                binding.layoutPlateau,
                state,
                taillPlateau,
                perso.getImageResId()
        );
    }
}
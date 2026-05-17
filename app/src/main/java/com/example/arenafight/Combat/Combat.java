package com.example.arenafight.Combat;

import android.content.Context;

import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.monstre.UtilisMonstre;
import com.example.arenafight.personnage.Personnage;

public class Combat {
    static final int TAILLE_PLATEAU = 10;
    int posJ = 0;
    int posM = TAILLE_PLATEAU - 1;
    public void lancerCombat(Context context, Personnage perso, ActivityCombatBinding binding, CombatState state) throws Exception {

        // créer le monstre seulement si pas encore créé
        if (state.monstre == null) {
            state.monstre = UtilisMonstre.tirerMonstre(context, perso.getLv());

            state.posJ = posJ;
            state.posM = posM;
            state.monstre.setPv(state.monstre.getPvMax());
            state.hpJMax = perso.getPv();
            state.hpJ = perso.getPvActuel();
            state.tourJoueur = true;
            state.combatLance = true;
        }

        Plateau.afficherPlateau(
                context,
                binding.layoutPlateau,
                state,
                TAILLE_PLATEAU,
                perso.getImageResId(),
                perso,
                binding
        );
        if (state.tourJoueur) {
            TourJoueur.tourJoueur(state, perso, context, binding);
        } else TourMonstre.tourMonstre(state, perso, context, binding);
    }
}
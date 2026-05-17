package com.example.arenafight.Combat;

import android.content.Context;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.personnage.Personnage;

public class TourMonstre {
    public static void tourMonstre(
            CombatState state, Personnage perso, Context context, ActivityCombatBinding binding) {

        if (state.tourJoueur) return;

        int distance = state.posM - state.posJ;
        if (distance <= 1) {
            int defence;
            if (state.joueurDefense) {
                defence = perso.getDef()*2;
                state.joueurDefense = false;
            }else {
                defence = perso.getDef();
            }

            int degats = Math.max(1,state.monstre.getAtq()-defence);
            perso.setPvActuel(perso.getPvActuel() - degats);
            state.hpJ = perso.getPvActuel();
        } else {
            // Déplacement monstre vers joueur
            state.posM--;
        }
        // fin du tour
        state.tourJoueur = true;
        FinTour.finTour(state, perso, context, binding);
    }
}

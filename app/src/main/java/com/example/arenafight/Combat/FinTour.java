package com.example.arenafight.Combat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.arenafight.ResultActivity;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.personnage.Personnage;

import static com.example.arenafight.Combat.Combat.TAILLE_PLATEAU;

public class FinTour {
    public static void finTour(CombatState state, Personnage perso, Context context, ActivityCombatBinding binding) {
        if (!state.monstre.estVivant()) {
            perso.setExp(perso.getExp() + 1);
            if (perso.getExp()==1) {
                perso.setExp(0);
                perso.levelUp();
            }
            Intent intent = new Intent(context, ResultActivity.class);

            intent.putExtra("victoire", true);
            intent.putExtra("perso", perso);

            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

            return;
        } else if (!perso.estVivant()) {
            Intent intent = new Intent(context, ResultActivity.class);

            intent.putExtra("victoire", false);
            intent.putExtra("perso", perso);

            context.startActivity(intent);
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }

            return;
        }else {

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
}

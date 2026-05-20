package com.example.arenafight.Combat;

import android.content.Context;
import android.view.View;
import com.example.arenafight.Combat.CombatState;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.personnage.Personnage;

import static com.example.arenafight.Combat.Combat.TAILLE_PLATEAU;

public class TourJoueur {
    public static void tourJoueur(
            CombatState state,
            Personnage perso,
            Context context,
            ActivityCombatBinding binding
    ) {

        if (!state.tourJoueur) return;
        binding.btnAtk1.setVisibility(View.VISIBLE);
        binding.btnAtk2.setVisibility(View.VISIBLE);
        binding.btnMove1.setVisibility(View.VISIBLE);

        Attaque[] attaques = getAttaquesJoueur(perso);
        Deplacement[] deplacements = getDeplacementJoueur(perso);

        // ATTACK 1
        binding.btnAtk1.setText(attaques[0].getNom());
        binding.btnAtk1.setOnClickListener(v -> {

            executerAttaque(state, perso, attaques[0]);
            endTurn(state, perso, context, binding);
        });

        // ATTACK 2
        if (attaques.length > 1) {

            binding.btnAtk2.setText(attaques[1].getNom());
            binding.btnAtk2.setOnClickListener(v -> {

                executerAttaque(state, perso, attaques[1]);
                endTurn(state, perso, context, binding);
            });
        }

        // MOVE
        binding.btnMove1.setText(deplacements[0].getNom());
        binding.btnMove1.setOnClickListener(v -> {

            Deplacement move = deplacements[0]; // ou sélection dynamique

            state.enModeDeplacement = true;
            state.distanceDeplacement = move.getDistance();

            Plateau.afficherPlateau(
                    context,
                    binding.layoutPlateau,
                    state,
                    TAILLE_PLATEAU,
                    perso.getImageResId(),
                    perso,
                    binding
            );
        });
        if (deplacements.length > 1) {

            binding.btnMove2.setVisibility(View.VISIBLE);
            binding.btnMove2.setText(deplacements[1].getNom());
            binding.btnMove2.setOnClickListener(v -> {

                state.enModeDeplacement = true;

                state.distanceDeplacement = deplacements[1].getDistance();

                Plateau.afficherPlateau(
                        context,
                        binding.layoutPlateau,
                        state,
                        TAILLE_PLATEAU,
                        perso.getImageResId(),
                        perso,
                        binding
                );
            });

        } else {
            binding.btnMove2.setVisibility(View.GONE);
        }
    }
    private static void endTurn(
            CombatState state,
            Personnage perso,
            Context context,
            ActivityCombatBinding binding
    ) {

        state.tourJoueur = false;

        FinTour.finTour(state, perso, context, binding);
    }
    public static void executerAttaque(
            CombatState state,
            Personnage perso,
            Attaque atk
    ) {

        int distance = Math.abs(state.posM - state.posJ);

        if (distance >= atk.getPorteeMin()
                && distance <= atk.getPorteeMax()) {

            int degats = (int)(
                    perso.getAtq() * atk.getBonusDegats()
            );

            state.monstre.setPv(
                    state.monstre.getPv() - degats
            );
        }
    }


    private static Attaque[] getAttaquesJoueur(Personnage perso) {
        if (perso.getClasse().equals("Guerrier")) {
            return new Attaque[]{
                    new Attaque("Coup d'épée", 1, 1, 1.2),
                    new Attaque("Arc", 2, 4, 0.8)
            };
        } else if (perso.getClasse().equals("Mage")) {
            return new Attaque[]{
                    new Attaque("Projectile magique", 1, 2, 1.3),
                    new Attaque("Boule de feu", 2, 5, 1)
            };
        } else if (perso.getClasse().equals("Assassin")) {
            return new Attaque[]{
                    new Attaque("cout de couteau", 1, 1, 1.3),
                    new Attaque("lancer de couteau", 2, 3, 0.7)
            };
        } else {
            return new Attaque[0];
        }
    }

    private static Deplacement[] getDeplacementJoueur(Personnage perso) {
        if (perso.getClasse().equals("Assassin")) {
            return new Deplacement[]{
                    new Deplacement("marche", 1),
                    new Deplacement("dash", 3)
            };
        } else {
            return new Deplacement[]{
                    new Deplacement("avancer", 1)
            };
        }
    }
}

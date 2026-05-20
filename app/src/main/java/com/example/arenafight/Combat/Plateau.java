package com.example.arenafight.Combat;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.arenafight.R;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.personnage.Personnage;

public class Plateau {
    public static boolean isMoveMode = false;
    public static void afficherPlateau(
            Context context,
            LinearLayout layoutPlateau,
            CombatState state,
            int taille,
            int imageJoueur,
            Personnage perso,
            ActivityCombatBinding binding
    ) {
        layoutPlateau.removeAllViews();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int caseSize = screenWidth / taille;

        for (int i = 0; i < taille; i++) {

            boolean isCaseAccessible;

            boolean isCasePortee;

            if (state.enModeAttaque) {

                int min = Math.max(0, state.posJ + state.porteeAttaqueMin);
                int max = Math.min(taille, state.posJ + state.porteeAttaqueMax);

                if (i >= min && i <= max && i != state.posJ) {
                    isCasePortee = true;
                } else {
                    isCasePortee = false;
                }
            } else {
                isCasePortee = false;
            }

            if (state.enModeDeplacement) {

                int min = Math.max(0, state.posJ - state.distanceDeplacement);
                int max = Math.min(state.posM - 1, state.posJ + state.distanceDeplacement);

                if (i >= min && i <= max && i != state.posJ) {
                    isCaseAccessible = true;
                } else {
                    isCaseAccessible = false;
                }
            } else {
                isCaseAccessible = false;
            }

            FrameLayout casePlateau = new FrameLayout(context);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(caseSize + 30, caseSize + 300);

            params.setMargins(-18, 0, -18, 0);
            casePlateau.setLayoutParams(params);

            ImageView imageCase = new ImageView(context);

            FrameLayout.LayoutParams imageParams =
                    new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                    );

            imageCase.setLayoutParams(imageParams);
            imageCase.setScaleType(ImageView.ScaleType.FIT_CENTER);

            int barreHeight = caseSize / 10;

            ProgressBar barreVie = new ProgressBar(
                    context,
                    null,
                    android.R.attr.progressBarStyleHorizontal
            );

            FrameLayout.LayoutParams barreParams =
                    new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            barreHeight
                    );

            barreParams.gravity = Gravity.TOP;
            barreParams.topMargin = 80;
            barreParams.leftMargin = 20;
            barreParams.rightMargin = 20;

            barreVie.setLayoutParams(barreParams);

            barreVie.setProgressDrawable(
                    AppCompatResources.getDrawable(context, R.drawable.barre_vie)
            );

            // JOUEUR
            if (i == state.posJ) {

                imageCase.setImageResource(imageJoueur);
                barreVie.setMax(state.hpJMax);
                barreVie.setProgress(state.hpJ);

                casePlateau.addView(barreVie);
            }

            // MONSTRE
            else if (i == state.posM) {

                imageCase.setImageResource(state.monstre.getImageResId());
                barreVie.setMax(state.monstre.getPvMax());
                barreVie.setProgress(state.monstre.getPv());

                casePlateau.addView(barreVie);

                if (state.enModeAttaque && isCasePortee) {
                    imageCase.setColorFilter(0x55FF0000); // rouge transparent
                }
            }

            // VIDE
            else {
                imageCase.setImageResource(R.drawable.case_vide);
                if (isCaseAccessible) {
                    imageCase.setColorFilter(0x550000FF); // bleu transparent
                } else if (isCasePortee) {
                    imageCase.setColorFilter(0x55FF0000); // rouge transparent
                }
            }

            final int index = i;

            casePlateau.setOnClickListener(v -> {

                if (state.enModeDeplacement && isCaseAccessible) {
                    state.posJ = index;
                    state.enModeDeplacement = false;
                } else if (state.enModeAttaque && isCasePortee) {
                    if (index == state.posM) {
                        int degats = (int)(
                                perso.getAtq() * state.degatAttaque
                        );

                        state.monstre.setPv(
                                state.monstre.getPv() - degats
                        );
                    }
                    state.enModeAttaque = false;
                } else return;

                // refresh plateau
                Plateau.afficherPlateau(
                        context,
                        layoutPlateau,
                        state,
                        taille,
                        imageJoueur,
                        perso,
                        binding
                );

                // fin du tour joueur
                state.tourJoueur = false;

                FinTour.finTour(
                        state,
                        perso,
                        context,
                        binding
                );
            });

            casePlateau.addView(imageCase);
            layoutPlateau.addView(casePlateau);
        }
    }
}
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

public class Plateau {

    public static void afficherPlateau(
            Context context,
            LinearLayout layoutPlateau,
            CombatState state,
            int taille,
            int imageJoueur
    ) {

        layoutPlateau.removeAllViews();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int caseSize = screenWidth / taille;

        for (int i = 0; i < taille; i++) {

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
            barreVie.setMax(100);
            barreVie.setProgress(100);

            barreVie.setProgressDrawable(
                    AppCompatResources.getDrawable(context, R.drawable.barre_vie)
            );

            // JOUEUR
            if (i == state.posJ) {

                imageCase.setImageResource(imageJoueur);
                casePlateau.addView(barreVie);
            }

            // MONSTRE
            else if (i == state.posM) {

                imageCase.setImageResource(state.monstre.getImageResId());
                casePlateau.addView(barreVie);
            }

            // VIDE
            else {
                imageCase.setImageResource(R.drawable.case_vide);
            }

            casePlateau.addView(imageCase);
            layoutPlateau.addView(casePlateau);
        }
    }
}
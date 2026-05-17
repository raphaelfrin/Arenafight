package com.example.arenafight.monstre;

import android.content.Context;

import com.example.arenafight.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UtilisMonstre {

    /**
     * Récupère tous les monstres depuis monstres.xml
     */
    public static List<Monstre> getMonstres(Context context) {

        List<Monstre> monstres = new ArrayList<>();

        String[] noms =
                context.getResources().getStringArray(R.array.noms_monstres);

        int[] niveauMin =
                context.getResources().getIntArray(R.array.niveauMin_monstres);

        int[] niveauMax =
                context.getResources().getIntArray(R.array.niveauMax_monstres);

        int[] pvMax =
                context.getResources().getIntArray(R.array.pv_monstres);

        int[] pv =
                context.getResources().getIntArray(R.array.pv_monstres);

        int[] atq =
                context.getResources().getIntArray(R.array.atq_monstres);

        int[] def =
                context.getResources().getIntArray(R.array.def_monstres);

        int[] images = {
                R.drawable.rat,
                R.drawable.gobelin,
                R.drawable.loup,
                R.drawable.orc,
                R.drawable.troll
        };

        for (int i = 0; i < noms.length; i++) {

            Monstre monstre = new Monstre(
                    noms[i],
                    niveauMin[i],
                    niveauMax[i],
                    pvMax[i],
                    pv[i],
                    atq[i],
                    def[i],
                    images[i]
            );

            monstres.add(monstre);
        }

        return monstres;
    }

    /**
     * Tire un monstre adapté au niveau du joueur
     */
    public static Monstre tirerMonstre(Context context,
                                       int lvJoueur) throws Exception {

        List<Monstre> monstres = getMonstres(context);

        List<Monstre> candidats = new ArrayList<>();

        for (Monstre m : monstres) {

            if (m.getNiveauMin() <= lvJoueur
                    && lvJoueur <= m.getNiveauMax()) {

                candidats.add(m);
            }
        }

        if (candidats.isEmpty()) {

            throw new Exception(
                    "Aucun monstre disponible pour le niveau "
                            + lvJoueur
            );
        }

        Random random = new Random();

        int index = random.nextInt(candidats.size());

        return candidats.get(index);
    }
}


package com.example.arenafight.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arenafight.personnage.Personnage;

public class PersonnagePreferences {

    private static final String PREF_NAME = "personnage_pref";

    // Clés
    private static final String KEY_NOM = "nom";
    private static final String KEY_CLASSE = "classe";
    private static final String KEY_PV = "pv";
    private static final String KEY_ATQ = "atq";
    private static final String KEY_DEF = "def";
    private static final String KEY_LV = "lv";
    private static final String KEY_PV_ACTUEL = "pv_actuel";
    private static final String KEY_MONSTRES = "monstres";
    private static final String KEY_MORT = "mort";

    // Sauvegarde
    public static void sauvegarderPersonnage(Context context, Personnage p) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(KEY_NOM, p.getNom());
        editor.putString(KEY_CLASSE, p.getClasse());

        editor.putInt(KEY_PV, p.getPv());
        editor.putInt(KEY_PV_ACTUEL, p.getPvActuel());

        editor.putInt(KEY_ATQ, p.getAtq());
        editor.putInt(KEY_DEF, p.getDef());

        editor.putInt(KEY_LV, p.getLv());

        editor.putBoolean(KEY_MORT, !p.estVivant());

        editor.apply();
    }

    // Vérifie si un personnage existe
    public static boolean personnageExiste(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.contains(KEY_NOM);
    }

    // Récupération des infos
    public static String getNom(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getString(KEY_NOM, "");
    }

    public static String getClasse(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getString(KEY_CLASSE, "");
    }

    public static int getMonstresVaincus(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_MONSTRES, 0);
    }

    public static void ajouterMonstreVaincu(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int actuel = prefs.getInt(KEY_MONSTRES, 0);

        prefs.edit()
                .putInt(KEY_MONSTRES, actuel + 1)
                .apply();
    }

    public static boolean estMort(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(KEY_MORT, false);
    }

    // Suppression totale
    public static void supprimerPersonnage(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        prefs.edit().clear().apply();
    }
}
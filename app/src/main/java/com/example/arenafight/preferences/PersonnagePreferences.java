package com.example.arenafight.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arenafight.personnage.Assassin;
import com.example.arenafight.personnage.Guerrier;
import com.example.arenafight.personnage.Mage;
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
    private static final String KEY_EXP = "Exp";
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
        editor.putInt(KEY_EXP, p.getExp());


        editor.putBoolean(KEY_MORT, !p.estVivant());

        editor.apply();
    }

    public static Personnage chargerPersonnage(Context context) {

        String nom = getNom(context);
        String classe = getClasse(context);

        Personnage p;

        switch (classe) {
            case "Guerrier":
                p = new Guerrier(nom);
                break;
            case "Mage":
                p = new Mage(nom);
                break;
            case "Assassin":
                p = new Assassin(nom);
                break;
            default:
                return null;
        }

        p.setPv(getPv(context));
        p.setPvActuel(getPvActuel(context));
        p.setAtq(getAtq(context));
        p.setDef(getDef(context));
        p.setLv(getLv(context));
        p.setExp(getExp(context));

        return p;
    }
    public static int getPv(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_PV, 0);
    }

    public static int getAtq(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_ATQ, 0);
    }

    public static int getDef(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_DEF, 0);
    }

    public static int getLv(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_LV, 1);
    }
    public static int getExp(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_EXP, 0);
    }

    public static int getPvActuel(Context context) {

        SharedPreferences prefs =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(KEY_PV_ACTUEL, 0);
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
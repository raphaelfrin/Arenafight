package com.example.arenafight.affichage;

import android.view.View;
import com.example.arenafight.databinding.ActivityMainBinding;

public class Visible {

    private final ActivityMainBinding binding;

    // On passe le binding au constructeur
    public Visible(ActivityMainBinding binding) {
        this.binding = binding;
    }

    // Méthode publique pour afficher/cacher
    public void afficherStats(boolean visible) {
        int vis = visible ? View.VISIBLE : View.GONE;
        binding.textViewPV.setVisibility(vis);
        binding.textViewATQ.setVisibility(vis);
        binding.textViewDEF.setVisibility(vis);
        binding.textViewLV.setVisibility(vis);
    }
}
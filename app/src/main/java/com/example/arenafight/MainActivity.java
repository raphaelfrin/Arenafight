package com.example.arenafight;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.arenafight.affichage.Visible;
import com.example.arenafight.databinding.ActivityMainBinding;
import com.example.arenafight.personnage.Assassin;
import com.example.arenafight.personnage.Guerrier;
import com.example.arenafight.personnage.Mage;
import com.example.arenafight.personnage.Personnage;
import com.example.arenafight.preferences.PersonnagePreferences;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Cache le bouton au démarrage
        binding.btnNouveau.setVisibility(View.GONE);

        // orientation de l'écran
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Écran en paysage
            Log.d(TAG, "Mode paysage");
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Écran en portrait
            Log.d(TAG, "Mode portrait");
        }

        // fermer le clavier au toucher
        binding.main.setOnClickListener(v -> closeKeyboard());

        //bouton jouer
        binding.buttonPlay.setOnClickListener(v -> {

            String nomJoueur = Objects.requireNonNull(binding.editTextNom.getText()).toString().trim();

            if (nomJoueur.isEmpty()) {
                Snackbar.make(binding.getRoot(), "Veuillez entrer votre nom", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (binding.SpinnerClasse.getSelectedItemPosition() == 0) {
                Snackbar.make(binding.getRoot(), "Choisis une classe avant de jouer !", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String classe = binding.SpinnerClasse.getSelectedItem().toString();
            Personnage perso = null;

            // Création de l'objet perso selon la classe choisie
            switch (classe) {
                case "Guerrier":
                    perso = new Guerrier(nomJoueur);
                    break;
                case "Mage":
                    perso = new Mage(nomJoueur);
                    break;
                case "Assassin":
                    perso = new Assassin(nomJoueur);
                    break;
            }

            if (perso != null) {
                PersonnagePreferences.sauvegarderPersonnage(this, perso);
                Intent intent = new Intent(MainActivity.this, CombatActivity.class);
                intent.putExtra("perso", (Serializable) perso);
                startActivity(intent);
            }
        });

        // Spinner selection classe
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.options_menu)

        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnerClasse.setAdapter(adapter);

        binding.SpinnerClasse.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                closeKeyboard();
                binding.editTextNom.clearFocus();

                v.performClick();
            }
            return false;
        });

        binding.SpinnerClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            final Visible visibleHelper = new Visible(binding);

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                closeKeyboard();
                binding.editTextNom.clearFocus();

                closeKeyboard();
                binding.editTextNom.clearFocus();

                if (position == 0) {
                    visibleHelper.afficherStats(false);
                    return;
                }
                visibleHelper.afficherStats(true);
                Personnage perso = null;

                String classe = parent.getItemAtPosition(position).toString();

                switch (classe) {
                    case "Guerrier":
                        perso = new Guerrier(Objects.requireNonNull(binding.editTextNom.getText()).toString());
                        break;

                    case "Mage":
                        perso = new Mage(Objects.requireNonNull(binding.editTextNom.getText()).toString());
                        break;

                    case "Assassin":
                        perso = new Assassin(Objects.requireNonNull(binding.editTextNom.getText()).toString());
                        break;
                }

                if (perso != null) {
                    binding.textViewPV.setText(MessageFormat.format("PV : {0}", perso.getPv()));
                    binding.textViewATQ.setText(MessageFormat.format("ATQ : {0}", perso.getAtq()));
                    binding.textViewDEF.setText(MessageFormat.format("DEF : {0}", perso.getDef()));
                    binding.textViewLV.setText(MessageFormat.format("Niveau : {0}", perso.getLv()));
                }
                Log.d(TAG, "Classe choisie : " + classe);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        // Vérifie si un personnage existe
        if(PersonnagePreferences.personnageExiste(this)) {

            // Affiche le bouton Nouveau personnage
            binding.btnNouveau.setVisibility(View.VISIBLE);

            // Préremplir le nom
            binding.editTextNom.setText(
                    PersonnagePreferences.getNom(this)
            );
            // Empêche modification du nom
            binding.editTextNom.setEnabled(false);

            // Afficher classe sélectionnée
            String classe =
                    PersonnagePreferences.getClasse(this);

            switch (classe) {

                case "Guerrier":
                    binding.SpinnerClasse.setSelection(1);
                    break;

                case "Mage":
                    binding.SpinnerClasse.setSelection(2);
                    break;

                case "Assassin":
                    binding.SpinnerClasse.setSelection(3);
                    break;
            }
            Visible visibleHelper = new Visible(binding);
            visibleHelper.afficherStats(true);

            binding.textViewPV.setText(
                    MessageFormat.format(
                            "PV : {0}/{1}",
                            PersonnagePreferences.getPvActuel(this),
                            PersonnagePreferences.getPv(this)
                    )
            );

            binding.textViewATQ.setText(
                    MessageFormat.format(
                            "ATQ : {0}",
                            PersonnagePreferences.getAtq(this)
                    )
            );

            binding.textViewDEF.setText(
                    MessageFormat.format(
                            "DEF : {0}",
                            PersonnagePreferences.getDef(this)
                    )
            );

            binding.textViewLV.setText(
                    MessageFormat.format(
                            "Niveau : {0}",
                            PersonnagePreferences.getLv(this)
                    )
            );
            // Désactive le spinner
            binding.SpinnerClasse.setEnabled(false);
            binding.SpinnerClasse.setClickable(false);
        }

        // Si le personnage est mort
        if(PersonnagePreferences.estMort(this)) {

            Toast.makeText(
                    this,
                    "Votre personnage est mort. Créez-en un nouveau.",
                    Toast.LENGTH_LONG
            ).show();

            binding.buttonPlay.setEnabled(false);
        }
        Button btnNouveau = findViewById(R.id.btnNouveau);
        binding.btnNouveau.setOnClickListener(v -> {

            // Efface les préférences
            PersonnagePreferences.supprimerPersonnage(this);

            // Réinitialise le nom
            binding.editTextNom.setText("");
            // Réactive le champ nom
            binding.editTextNom.setEnabled(true);

            // Réactive le spinner
            binding.SpinnerClasse.setEnabled(true);
            binding.SpinnerClasse.setClickable(true);
            // Reset spinner
            binding.SpinnerClasse.setSelection(0);

            // Cache le bouton après suppression
            binding.btnNouveau.setVisibility(View.GONE);

            Visible visibleHelper = new Visible(binding);
            visibleHelper.afficherStats(false);



            binding.buttonPlay.setEnabled(true);

            Toast.makeText(
                    this,
                    "Nouveau personnage créé",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}
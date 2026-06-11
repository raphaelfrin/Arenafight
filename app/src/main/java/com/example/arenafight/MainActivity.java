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
import com.example.arenafight.audio.AudioManager;
import com.example.arenafight.audio.MusicType;
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
    private boolean isLoading = false;
    private Personnage currentPerso;
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

        AudioManager.getInstance().playMusic(this, MusicType.COMBAT);

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
            if (currentPerso != null) {
                if (PersonnagePreferences.estMort(this)) {
                    Snackbar.make(
                            binding.getRoot(),
                            "Votre personnage est mort. Créez-en un nouveau.",
                            Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }

                if (currentPerso.getLv() >= 10) {
                    Snackbar.make(
                            binding.getRoot(),
                            "Votre personnage n'a plus aucun adversaire à affronter. Créez-en un nouveau.",
                            Snackbar.LENGTH_LONG
                    ).show();
                    return;
                }
            }

            String nomJoueur = Objects.requireNonNull(binding.editTextNom.getText()).toString().trim();

            if (nomJoueur.isEmpty()) {
                Snackbar.make(binding.getRoot(), "Veuillez entrer votre nom", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (binding.SpinnerClasse.getSelectedItemPosition() == 0) {
                Snackbar.make(binding.getRoot(), "Choisis une classe avant de jouer !", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Personnage perso;

            String classe = binding.SpinnerClasse.getSelectedItem().toString();

            if (currentPerso != null && currentPerso.getClasse().equals(classe)) {
                // on continue le vrai perso seulement si même classe
                perso = currentPerso;
            } else {
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
                    default:
                        return;
                }
            }

            if (perso != null) {
                PersonnagePreferences.sauvegarderPersonnage(this, perso);
                Intent intent = new Intent(MainActivity.this, CombatActivity.class);
                intent.putExtra("perso", (Serializable) perso);
                startActivity(intent);
            }
        });

        // Spinner selection classe
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,   // 👈 TON layout custom
                getResources().getStringArray(R.array.options_menu)
        ) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item);
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

                if (isLoading) return;

                if (position == 0) {
                    visibleHelper.afficherStats(false);
                    return;
                }

                visibleHelper.afficherStats(true);

                String classe = parent.getItemAtPosition(position).toString();

                // JUSTE preview, PAS de logique perso global
                Personnage preview = createPreview(classe);

                updateStats(preview);

                Log.d(TAG, "Preview classe : " + classe);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        AudioManager.getInstance().pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioManager.getInstance().resumeMusic();
        isLoading = true;

        if (PersonnagePreferences.personnageExiste(this)) {

            currentPerso = PersonnagePreferences.chargerPersonnage(this);

            binding.btnNouveau.setVisibility(View.VISIBLE);

            binding.editTextNom.setText(currentPerso.getNom());
            binding.editTextNom.setEnabled(false);

            binding.SpinnerClasse.setEnabled(false);
            binding.SpinnerClasse.setClickable(false);

            isLoading = true;

            // on désactive le listener temporairement
            AdapterView.OnItemSelectedListener oldListener = binding.SpinnerClasse.getOnItemSelectedListener();
            binding.SpinnerClasse.setOnItemSelectedListener(null);

            // on applique la sélection SANS trigger onItemSelected
            switch (currentPerso.getClasse()) {
                case "Guerrier":
                    binding.SpinnerClasse.setSelection(1, false);
                    break;
                case "Mage":
                    binding.SpinnerClasse.setSelection(2, false);
                    break;
                case "Assassin":
                    binding.SpinnerClasse.setSelection(3, false);
                    break;
            }

            // on remet le listener
            binding.SpinnerClasse.setOnItemSelectedListener(oldListener);

            isLoading = false;
            updateStats(currentPerso);
        } else {
            currentPerso = null;
        }

        isLoading = false;



        // Si le personnage est mort
        if(PersonnagePreferences.estMort(this)) {
            Toast.makeText(
                    this,
                    "Votre personnage est mort. Créez-en un nouveau.",
                    Toast.LENGTH_LONG
            ).show();
        }

        if (currentPerso != null && currentPerso.getLv() >= 10) {
            Toast.makeText(
                    this,
                    "Votre personnage n'a plus aucun adversaire à affronter. Créez-en un nouveau.",
                    Toast.LENGTH_LONG
            ).show();
        }

        binding.btnNouveau.setOnClickListener(v -> {

            PersonnagePreferences.supprimerPersonnage(this);

            currentPerso = null; // IMPORTANT

            // reset UI complet
            binding.editTextNom.setText("");
            binding.editTextNom.setEnabled(true);

            binding.SpinnerClasse.setEnabled(true);
            binding.SpinnerClasse.setClickable(true);

            isLoading = true;
            binding.SpinnerClasse.setSelection(0);
            isLoading = false;

            binding.btnNouveau.setVisibility(View.GONE);

            // RESET STATS IMPORTANT
            binding.textViewPV.setText("PV : -");
            binding.textViewATQ.setText("ATQ : -");
            binding.textViewDEF.setText("DEF : -");
            binding.textViewLV.setText("Niveau : -");

            Visible visibleHelper = new Visible(binding);
            visibleHelper.afficherStats(false);

            binding.buttonPlay.setEnabled(true);

            Toast.makeText(this, "Nouveau personnage créé", Toast.LENGTH_SHORT).show();
        });
    }

    private Personnage createPreview(String classe) {

        String nom = binding.editTextNom.getText().toString();
        if (nom.isEmpty()) nom = "Preview";

        switch (classe) {
            case "Guerrier":
                return new Guerrier(nom);
            case "Mage":
                return new Mage(nom);
            case "Assassin":
                return new Assassin(nom);
            default:
                return null;
        }
    }
    private void updateStats(Personnage p) {
        if (p == null) return;

        binding.textViewPV.setText("PV : " + p.getPv());
        binding.textViewATQ.setText("ATQ : " + p.getAtq());
        binding.textViewDEF.setText("DEF : " + p.getDef());
        binding.textViewLV.setText("Niveau : " + p.getLv());
    }
}
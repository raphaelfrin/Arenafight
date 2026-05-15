package com.example.arenafight;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.arenafight.Combat.Combat;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.monstre.Monstre;
import com.example.arenafight.monstre.UtilisMonstre;
import com.example.arenafight.personnage.Assassin;
import com.example.arenafight.personnage.Guerrier;
import com.example.arenafight.personnage.Mage;
import com.example.arenafight.personnage.Personnage;
import com.example.arenafight.preferences.PersonnagePreferences;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Objects;

import static com.example.arenafight.Combat.Combat.lancerCombat;

public class CombatActivity extends AppCompatActivity {
    ActivityCombatBinding binding;
    private boolean combatLance = false;

    private int posJ = 0;
    private int posM = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCombatBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Personnage perso;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            perso = getIntent().getSerializableExtra(
                    "perso",
                    Personnage.class
            );

        } else {

            @SuppressWarnings("deprecation")
            Personnage temp = (Personnage)
                    getIntent().getSerializableExtra("perso");

            perso = temp;
        }

        if (perso == null) {

            Toast.makeText(
                    this,
                    "Erreur chargement personnage",
                    Toast.LENGTH_LONG
            ).show();

            finish();
            return;
        }

        if (savedInstanceState != null) {

            combatLance =
                    savedInstanceState.getBoolean(
                            "combatLance"
                    );

            posJ =
                    savedInstanceState.getInt("posJ");

            posM =
                    savedInstanceState.getInt("posM");
        }

        getOnBackPressedDispatcher().addCallback(this,
                new androidx.activity.OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        // Combat lancé → quitte l'app
                        if (combatLance) {

                            moveTaskToBack(true);
                        }

                        // Sinon retour normal
                        else {

                            finish();
                        }
                    }
                });

        if (combatLance) {
            binding.imageJoueur.setVisibility(View.GONE);

        } else {
            binding.imageJoueur.setVisibility(View.VISIBLE);

            binding.imageJoueur.setImageResource(
                    perso.getImageResId()
            );

        }
        // Cache les barres de vie
        binding.barVieJoueur.setVisibility(View.GONE);
        binding.barVieMonstre.setVisibility(View.GONE);

        // Cache les images
        binding.imageMonstre.setVisibility(View.GONE);

        //bouton fight
        binding.btnFight.setOnClickListener(v -> {
            combatLance = true;
            binding.imageJoueur.setVisibility(View.GONE);

            try {
                Combat.lancerCombat(this, perso, binding);

                // Cache les boutons
                binding.btnFight.setVisibility(android.view.View.GONE);
                binding.Retour.setVisibility(android.view.View.GONE);

            }
            catch (Exception e) {

                Toast.makeText(
                        this,
                        e.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        binding.Retour.setOnClickListener(v -> finish());

        if (combatLance) {

            binding.btnFight.setVisibility(View.GONE);
            binding.Retour.setVisibility(View.GONE);

            binding.layoutPlateau.setVisibility(View.VISIBLE);

            Combat.lancerCombat(this, perso, binding);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("combatLance", combatLance);

        outState.putInt("posJ", posJ);
        outState.putInt("posM", posM);
    }
}

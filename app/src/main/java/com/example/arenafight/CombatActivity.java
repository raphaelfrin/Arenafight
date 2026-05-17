package com.example.arenafight;


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
import com.example.arenafight.Combat.CombatState;
import com.example.arenafight.Combat.TourJoueur;
import com.example.arenafight.databinding.ActivityCombatBinding;
import com.example.arenafight.personnage.Personnage;

public class CombatActivity extends AppCompatActivity {
    ActivityCombatBinding binding;
    private CombatState state;

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

        getOnBackPressedDispatcher().addCallback(this,
                new androidx.activity.OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        // Combat lancé → quitte l'app
                        if (state.combatLance) {
                            moveTaskToBack(true);
                        }

                        // Sinon retour normal
                        else {

                            finish();
                        }
                    }
                });

        binding.btnAtk1.setVisibility(View.GONE);
        binding.btnAtk2.setVisibility(View.GONE);
        binding.btnMove1.setVisibility(View.GONE);
        binding.btnMove2.setVisibility(View.GONE);

        if (savedInstanceState != null) {

            state = (CombatState) savedInstanceState.getSerializable("state");
            if (state == null) {
                state = new CombatState();
                state.combatLance = false;
            }

        } else {

            state = new CombatState();
            state.combatLance = false;
        }

        if (state != null && state.combatLance) {

            try {

                Combat combat = new Combat();

                combat.lancerCombat(
                        this,
                        perso,
                        binding,
                        state
                );

            } catch (Exception e) {
                e.printStackTrace();
            }

            binding.imageJoueur.setVisibility(View.GONE);
            binding.btnFight.setVisibility(View.GONE);
            binding.Retour.setVisibility(View.GONE);

            if (state.tourJoueur) {

                TourJoueur.tourJoueur(
                        state,
                        perso,
                        this,
                        binding
                );
            }
        } else {
            binding.imageJoueur.setVisibility(View.VISIBLE);

            binding.imageJoueur.setImageResource(
                    perso.getImageResId()
            );

        }

        //bouton fight
        binding.btnFight.setOnClickListener(v -> {

            state.combatLance = true;
            binding.btnFight.setVisibility(android.view.View.GONE);
            binding.Retour.setVisibility(android.view.View.GONE);
            binding.imageJoueur.setVisibility(View.GONE);

            try {
                Combat combat = new Combat();
                combat.lancerCombat(this, perso, binding, state);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.Retour.setOnClickListener(v -> finish());
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("state", state);
    }
}

package com.example.arenafight;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.arenafight.databinding.ActivityResultBinding;
import com.example.arenafight.personnage.Personnage;
import com.example.arenafight.preferences.PersonnagePreferences;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityResultBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });

        boolean victoire =
                getIntent().getBooleanExtra("victoire", false);

        Personnage perso;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            perso = getIntent().getSerializableExtra(
                    "perso",
                    Personnage.class
            );

        } else {

            perso = (Personnage)
                    getIntent().getSerializableExtra("perso");
        }

        if (perso == null) {
            finish();
            return;
        }

        // 🟢 VICTOIRE
        if (victoire) {

            binding.txtResult.setText("Victoire !");

            String stats =
                    "Niveau : " + perso.getLv()
                            + "\nXP : " + perso.getExp()
                            + "/5"
                            + "\nPV : " + perso.getPvActuel()
                            + "/" + perso.getPv()
                            + "\nATQ : " + perso.getAtq()
                            + "\nDEF : " + perso.getDef();

            binding.txtStats.setText(stats);

            binding.btnAction.setText("Nouveau combat");

            binding.btnAction.setOnClickListener(v -> {

                Intent intent =
                        new Intent(this, CombatActivity.class);

                intent.putExtra("perso", perso);

                startActivity(intent);

                finish();
            });
        }

        // 🔴 DEFAITE
        else {

            binding.txtResult.setText("Défaite...");

            binding.txtStats.setText(
                    perso.getNom() + " est mort."
            );
            //pass l'etat a mort

            binding.btnAction.setText("Retour menu");

            binding.btnAction.setOnClickListener(v -> {

                Intent intent =
                        new Intent(this, MainActivity.class);

                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                );

                startActivity(intent);

                finish();
            });
        }
    }
}
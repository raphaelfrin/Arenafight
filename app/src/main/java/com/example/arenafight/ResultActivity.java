package com.example.arenafight;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
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

        // VICTOIRE
        if (victoire) {

            binding.txtResult.setText(R.string.victoire);
            // on SAUVEGARDE
            PersonnagePreferences.sauvegarderPersonnage(this, perso);

            String stats =
                    "Niveau : " + perso.getLv()
                            + "\nXP : " + perso.getExp()
                            + "/5"
                            + "\nPV : " + perso.getPvActuel()
                            + "/" + perso.getPv()
                            + "\nATQ : " + perso.getAtq()
                            + "\nDEF : " + perso.getDef();

            binding.txtStats.setText(stats);

            if (perso.getLv() >= 10) {
                binding.btnAction.setText(R.string.retour_menu);

                binding.btnAction.setOnClickListener(v -> {

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    finish();
                });
            } else {
                binding.btnAction.setText(R.string.nouveau_combat);

                binding.btnAction.setOnClickListener(v -> {

                    Intent intent = new Intent(this, CombatActivity.class);
                    intent.putExtra("perso", perso);
                    startActivity(intent);
                    finish();
                });
            }
        }

        // DEFAITE
        else {

            binding.txtResult.setText(R.string.d_faite);

            binding.txtStats.setText(perso.getNom() + getString(R.string.est_ko));

            // on SAUVEGARDE
            PersonnagePreferences.sauvegarderPersonnage(this, perso);

            binding.btnAction.setText(R.string.retour__menu);

            binding.btnAction.setOnClickListener(v -> {

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
            });
        }
    }
}
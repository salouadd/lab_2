package com.example.lab2;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText inputSurface, inputPieces;
    private Switch switchPiscine, switchGarage, switchSolaire;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputSurface  = findViewById(R.id.input_surface);
        inputPieces   = findViewById(R.id.input_pieces);
        switchPiscine = findViewById(R.id.switch_piscine);
        switchGarage  = findViewById(R.id.switch_garage);
        switchSolaire = findViewById(R.id.switch_solaire);
        result        = findViewById(R.id.result);

        findViewById(R.id.button_calcul).setOnClickListener(v -> calculer());
    }

    private void calculer() {
        String sSurface = inputSurface.getText().toString().trim();
        String sPieces  = inputPieces.getText().toString().trim();

        if (sSurface.isEmpty() || sPieces.isEmpty()) {
            Toast.makeText(this, "Remplissez tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double surface = Double.parseDouble(sSurface);
            int    pieces  = Integer.parseInt(sPieces);

            double base       = surface * 2;
            double suppPieces = pieces * 50;
            double taxPiscine = switchPiscine.isChecked() ? 800 : 0;
            double taxGarage  = switchGarage.isChecked()  ? 200 : 0;
            double redSolaire = switchSolaire.isChecked() ? 150 : 0;
            double total      = base + suppPieces + taxPiscine + taxGarage - redSolaire;

            result.setText(
                    "Base surface : "    + base       + " DH\n" +
                            "Supplément pièces : " + suppPieces + " DH\n" +
                            (switchPiscine.isChecked() ? "Piscine : +800 DH\n" : "") +
                            (switchGarage.isChecked()  ? "Garage : +200 DH\n"  : "") +
                            (switchSolaire.isChecked() ? "Solaire : −150 DH\n" : "") +
                            "\nTotal : " + total + " DH"
            );
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer des nombres valides.", Toast.LENGTH_SHORT).show();
        }
    }
}
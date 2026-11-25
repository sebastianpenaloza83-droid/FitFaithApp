package com.example.fitfaithapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CuestionarioActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private ProgressBar progressBar;

    private ChipGroup chipGroupHorarios;
    private EditText etPesoKg, etAlturaCm;
    private ChipGroup chipGroupSalud;

    private TextView tvResumenObjetivo, tvResumenHistorial, tvResumenHorario, tvResumenMedidas, tvResumenSalud;

    private String objetivoSeleccionado = "";
    private String historialSeleccionado = "";
    private String horarioSeleccionado = "No especificado";
    private String medidasTexto = "No especificadas";
    private String saludSeleccionada = "No especificado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        viewFlipper = findViewById(R.id.viewFlipperCuestionario);
        progressBar = findViewById(R.id.progressBarCuestionario);

        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        // --- PASO 1: OBJETIVO ---
        CardView cardPerderPeso = findViewById(R.id.cardPerderPeso);
        CardView cardMejorarForma = findViewById(R.id.cardMejorarForma);
        CardView cardGanarMasa = findViewById(R.id.cardGanarMasa);

        cardPerderPeso.setOnClickListener(v -> {
            objetivoSeleccionado = "Perder peso";
            irAPaso(1, 33);
        });

        cardMejorarForma.setOnClickListener(v -> {
            objetivoSeleccionado = "Mejorar la forma física y la calidad de vida.";
            irAPaso(1, 33);
        });

        cardGanarMasa.setOnClickListener(v -> {
            objetivoSeleccionado = "Ganar masa muscular";
            irAPaso(1, 33);
        });

        // --- PASO 2: HISTORIAL ---
        CardView cardNoEntrene = findViewById(R.id.cardNoEntrene);
        CardView cardEntrene1x = findViewById(R.id.cardEntrene1x);
        CardView cardEntrene2a3x = findViewById(R.id.cardEntrene2a3x);
        CardView cardEntrene4xMas = findViewById(R.id.cardEntrene4xMas);

        cardNoEntrene.setOnClickListener(v -> {
            historialSeleccionado = "No entrené";
            irAPaso(2, 50);
        });

        cardEntrene1x.setOnClickListener(v -> {
            historialSeleccionado = "Entrené 1x a la semana";
            irAPaso(2, 50);
        });

        cardEntrene2a3x.setOnClickListener(v -> {
            historialSeleccionado = "Entrené de 2x a 3x a la semana";
            irAPaso(2, 50);
        });

        cardEntrene4xMas.setOnClickListener(v -> {
            historialSeleccionado = "Entrené 4x o más a la semana";
            irAPaso(2, 50);
        });

        // --- PASO 3: HORARIOS ---
        chipGroupHorarios = findViewById(R.id.chipGroupHorarios);
        Button btnSiguienteHorario = findViewById(R.id.btnSiguienteHorario);

        btnSiguienteHorario.setOnClickListener(v -> {
            int checkedId = chipGroupHorarios.getCheckedChipId();
            if (checkedId != ViewFlipper.NO_ID) {
                Chip chipElegido = findViewById(checkedId);
                horarioSeleccionado = chipElegido.getText().toString();
            } else {
                horarioSeleccionado = "No especificado";
            }
            irAPaso(3, 68);
        });

        // --- PASO 4: PESO Y ESTATURA ---
        etPesoKg = findViewById(R.id.etPesoKg);
        etAlturaCm = findViewById(R.id.etAlturaCm);
        Button btnSiguienteMedidas = findViewById(R.id.btnSiguienteMedidas);

        btnSiguienteMedidas.setOnClickListener(v -> {
            String peso = etPesoKg.getText().toString().trim();
            String altura = etAlturaCm.getText().toString().trim();

            if (!peso.isEmpty() && !altura.isEmpty()) {
                medidasTexto = peso + " kg - " + altura + " cm";
            } else if (!peso.isEmpty()) {
                medidasTexto = peso + " kg - Altura no indicada";
            } else if (!altura.isEmpty()) {
                medidasTexto = "Peso no indicado - " + altura + " cm";
            } else {
                medidasTexto = "No especificadas";
            }
            irAPaso(4, 85);
        });

        // --- PASO 5: SALUD / ENFERMEDAD ---
        chipGroupSalud = findViewById(R.id.chipGroupSalud);
        Button btnSiguienteSalud = findViewById(R.id.btnSiguienteSalud);

        btnSiguienteSalud.setOnClickListener(v -> {
            List<String> seleccionados = new ArrayList<>();
            List<Integer> idsSeleccionados = chipGroupSalud.getCheckedChipIds();

            for (int id : idsSeleccionados) {
                Chip chip = findViewById(id);
                seleccionados.add(chip.getText().toString());
            }

            if (seleccionados.isEmpty()) {
                saludSeleccionada = "No especificado";
            } else {
                saludSeleccionada = String.join(", ", seleccionados);
            }

            cargarResumenConfirmacion();
            irAPaso(5, 100);
        });

        // --- PASO 6: CONFIRMACIÓN ---
        tvResumenObjetivo = findViewById(R.id.tvResumenObjetivo);
        tvResumenHistorial = findViewById(R.id.tvResumenHistorial);
        tvResumenHorario = findViewById(R.id.tvResumenHorario);
        tvResumenMedidas = findViewById(R.id.tvResumenMedidas);
        tvResumenSalud = findViewById(R.id.tvResumenSalud);

        Button btnConfirmarRespuestas = findViewById(R.id.btnConfirmarRespuestas);
        Button btnCambiarRespuestas = findViewById(R.id.btnCambiarRespuestas);

        btnConfirmarRespuestas.setOnClickListener(v -> {
            Toast.makeText(this, "Perfil guardado. Generando rutina sugerida...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CuestionarioActivity.this, RutinaSugeridaActivity.class);
            intent.putExtra("OBJETIVO_USUARIO", objetivoSeleccionado);
            intent.putExtra("HISTORIAL_USUARIO", historialSeleccionado);
            intent.putExtra("HORARIO_USUARIO", horarioSeleccionado);
            intent.putExtra("MEDIDAS_USUARIO", medidasTexto);
            intent.putExtra("SALUD_USUARIO", saludSeleccionada);
            intent.putExtra("NOMBRE_USUARIO", getIntent().getStringExtra("NOMBRE_USUARIO"));
            intent.putExtra("ROL_USUARIO", getIntent().getStringExtra("ROL_USUARIO"));
            startActivity(intent);
            finish();
        });

        btnCambiarRespuestas.setOnClickListener(v -> irAPaso(0, 16));
    }

    private void irAPaso(int indicePantalla, int porcentajeProgreso) {
        progressBar.setProgress(porcentajeProgreso);
        viewFlipper.setDisplayedChild(indicePantalla);
    }

    private void cargarResumenConfirmacion() {
        tvResumenObjetivo.setText(objetivoSeleccionado.isEmpty() ? "-" : objetivoSeleccionado);
        tvResumenHistorial.setText(historialSeleccionado.isEmpty() ? "-" : historialSeleccionado);
        tvResumenHorario.setText(horarioSeleccionado);
        tvResumenMedidas.setText(medidasTexto);
        tvResumenSalud.setText(saludSeleccionada);
    }
}
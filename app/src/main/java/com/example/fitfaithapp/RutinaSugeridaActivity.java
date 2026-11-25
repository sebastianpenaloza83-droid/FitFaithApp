package com.example.fitfaithapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RutinaSugeridaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_sugerida);

        String objetivo = getIntent().getStringExtra("OBJETIVO_USUARIO");
        String historial = getIntent().getStringExtra("HISTORIAL_USUARIO");
        String horario = getIntent().getStringExtra("HORARIO_USUARIO");
        String medidas = getIntent().getStringExtra("MEDIDAS_USUARIO");
        String salud = getIntent().getStringExtra("SALUD_USUARIO");
        String nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        String rolUsuario = getIntent().getStringExtra("ROL_USUARIO");

        if (objetivo == null) objetivo = "Mejorar la forma física y la calidad de vida.";
        if (historial == null) historial = "No entrené";

        TextView tvTagObjetivo = findViewById(R.id.tvTagObjetivo);
        TextView tvTagEnfoque = findViewById(R.id.tvTagEnfoque);
        TextView tvFrecuenciaIdeal = findViewById(R.id.tvFrecuenciaIdeal);
        TextView tvNombreRutina = findViewById(R.id.tvNombreRutinaSugerida);
        TextView tvDetalleRutina = findViewById(R.id.tvDetalleRutinaSugerida);

        String nombreRutinaSugerida;
        String detalleRutinaSugerida;
        String enfoqueTag;

        if (objetivo.toLowerCase().contains("perder peso")) {
            tvTagObjetivo.setText("Pérdida de Peso");
            enfoqueTag = "Cardio + Funcional";
            nombreRutinaSugerida = "Funcionales";
            detalleRutinaSugerida = "30 min · 6 ejercicios";
        } else if (objetivo.toLowerCase().contains("masa muscular")) {
            tvTagObjetivo.setText("Masa Muscular");
            enfoqueTag = "Fuerza y Potencia";
            nombreRutinaSugerida = "Fuerza y Potencia";
            detalleRutinaSugerida = "45 min · 7 ejercicios";
        } else {
            tvTagObjetivo.setText("Forma Física");
            enfoqueTag = "Resistencia";
            nombreRutinaSugerida = "Cardio y Resistencia";
            detalleRutinaSugerida = "35 min · 6 ejercicios";
        }

        tvTagEnfoque.setText(enfoqueTag);
        tvFrecuenciaIdeal.setText("Perfil: " + historial);
        tvNombreRutina.setText(nombreRutinaSugerida);
        tvDetalleRutina.setText(detalleRutinaSugerida);

        Button btnMeGusta = findViewById(R.id.btnMeGustaEntrenemos);
        Button btnVerOtros = findViewById(R.id.btnVerOtrasRutinas);

        String finalObjetivo = objetivo;
        String finalHistorial = historial;

        btnMeGusta.setOnClickListener(v -> {
            Intent intent = new Intent(RutinaSugeridaActivity.this, MenuPrincipalActivity.class);
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario);
            intent.putExtra("ROL_USUARIO", rolUsuario);
            intent.putExtra("OBJETIVO_USUARIO", finalObjetivo);
            intent.putExtra("HISTORIAL_USUARIO", finalHistorial);
            intent.putExtra("HORARIO_USUARIO", horario);
            intent.putExtra("MEDIDAS_USUARIO", medidas);
            intent.putExtra("SALUD_USUARIO", salud);
            intent.putExtra("RUTINA_SUGERIDA", nombreRutinaSugerida);
            intent.putExtra("DETALLE_RUTINA", detalleRutinaSugerida);
            startActivity(intent);
            finish();
        });

        btnVerOtros.setOnClickListener(v -> {
            Intent intent = new Intent(RutinaSugeridaActivity.this, MenuPrincipalActivity.class);
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario);
            intent.putExtra("ROL_USUARIO", rolUsuario);
            intent.putExtra("RUTINA_SUGERIDA", nombreRutinaSugerida);
            intent.putExtra("DETALLE_RUTINA", detalleRutinaSugerida);
            intent.putExtra("ABRIR_RUTINAS", true);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.fitfaithapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InicioFragment extends Fragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_RUTINA = "rutina";
    private static final String ARG_DETALLE = "detalle";

    public InicioFragment() {
    }

    public static InicioFragment newInstance(String nombre, String rutina, String detalle) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_RUTINA, rutina);
        args.putString(ARG_DETALLE, detalle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String nombreUsuario = "";
        String rutinaSugerida = "Funcionales";
        String detalleRutina = "30 min · 6 ejercicios";

        if (getArguments() != null) {
            nombreUsuario = getArguments().getString(ARG_NOMBRE, "");
            String rutinaArg = getArguments().getString(ARG_RUTINA);
            String detalleArg = getArguments().getString(ARG_DETALLE);
            if (rutinaArg != null && !rutinaArg.isEmpty()) rutinaSugerida = rutinaArg;
            if (detalleArg != null && !detalleArg.isEmpty()) detalleRutina = detalleArg;
        }

        TextView tvSaludo = view.findViewById(R.id.tvSaludoUsuario);
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            tvSaludo.setText("¡Hola, " + nombreUsuario + "! Este es tu plan semanal");
        } else {
            tvSaludo.setText("¡Listo para entrenar!");
        }

        TextView tvRutinaActiva = view.findViewById(R.id.tvRutinaActivaNombre);
        TextView tvDetalleActiva = view.findViewById(R.id.tvRutinaActivaDetalle);
        tvRutinaActiva.setText(rutinaSugerida);
        tvDetalleActiva.setText(detalleRutina);

        configurarCalendarioSemanal(view);

        View cardEntrenamiento = view.findViewById(R.id.cardMiEntrenamiento);
        cardEntrenamiento.setOnClickListener(v ->
                Toast.makeText(getContext(), "Abriendo rutina: " + tvRutinaActiva.getText(), Toast.LENGTH_SHORT).show()
        );
    }

    private void configurarCalendarioSemanal(View view) {
        TextView[] tvDiasLetra = {
                view.findViewById(R.id.tvNombreDia0),
                view.findViewById(R.id.tvNombreDia1),
                view.findViewById(R.id.tvNombreDia2),
                view.findViewById(R.id.tvNombreDia3),
                view.findViewById(R.id.tvNombreDia4),
                view.findViewById(R.id.tvNombreDia5),
                view.findViewById(R.id.tvNombreDia6)
        };

        TextView[] tvDiasNumero = {
                view.findViewById(R.id.tvNumDia0),
                view.findViewById(R.id.tvNumDia1),
                view.findViewById(R.id.tvNumDia2),
                view.findViewById(R.id.tvNumDia3),
                view.findViewById(R.id.tvNumDia4),
                view.findViewById(R.id.tvNumDia5),
                view.findViewById(R.id.tvNumDia6)
        };

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatoLetra = new SimpleDateFormat("EEEEE", new Locale("es", "CO"));
        SimpleDateFormat formatoNumero = new SimpleDateFormat("d", new Locale("es", "CO"));

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                tvDiasLetra[i].setText("Hoy");
            } else {
                String letra = formatoLetra.format(calendar.getTime()).toUpperCase();
                tvDiasLetra[i].setText(letra);
            }
            tvDiasNumero[i].setText(formatoNumero.format(calendar.getTime()));

            final String fechaTocada = (i == 0 ? "Hoy " : "") + formatoNumero.format(calendar.getTime());
            tvDiasNumero[i].setOnClickListener(v ->
                    Toast.makeText(getContext(), "Día seleccionado: " + fechaTocada, Toast.LENGTH_SHORT).show()
            );

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
package com.example.fitfaithapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class RutinaFragment extends Fragment {

    public RutinaFragment() {
        // Constructor público vacío requerido por Android
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Esto es lo que conecta el código con tu diseño visual de las tarjetas
        return inflater.inflate(R.layout.fragment_rutina, container, false);
    }
}
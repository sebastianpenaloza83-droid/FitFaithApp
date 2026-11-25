package com.example.fitfaithapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipalActivity extends AppCompatActivity {

    private String nombreUsuario;
    private String rutinaSugerida;
    private String detalleRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contenedor_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        rutinaSugerida = getIntent().getStringExtra("RUTINA_SUGERIDA");
        detalleRutina = getIntent().getStringExtra("DETALLE_RUTINA");
        boolean abrirRutinas = getIntent().getBooleanExtra("ABRIR_RUTINAS", false);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                cargarFragment(InicioFragment.newInstance(nombreUsuario, rutinaSugerida, detalleRutina));
                return true;
            } else if (id == R.id.nav_rutina) {
                cargarFragment(new RutinaFragment());
                return true;
            } else if (id == R.id.nav_salud) {
                Toast.makeText(this, "Sección Salud", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_mas) {
                Toast.makeText(this, "Sección Más", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_cuenta) {
                Toast.makeText(this, "Sección Cuenta", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            if (abrirRutinas) {
                bottomNav.setSelectedItemId(R.id.nav_rutina);
                cargarFragment(new RutinaFragment());
            } else {
                bottomNav.setSelectedItemId(R.id.nav_inicio);
                cargarFragment(InicioFragment.newInstance(nombreUsuario, rutinaSugerida, detalleRutina));
            }
        }
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor_menu, fragment)
                .commit();
    }
}
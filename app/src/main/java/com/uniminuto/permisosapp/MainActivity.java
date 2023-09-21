package com.uniminuto.permisosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private TextView tvCamara;
    private TextView tvContactos;
    private TextView tvAlmacenamiento;
    private Button btnCheckPermissions;
    private Button btnRequestPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicio();
        statusBtnRequestPermissions();

        btnCheckPermissions.setOnClickListener(this::verificarPermisos);
        btnRequestPermissions.setOnClickListener(this::solicitarPermisos);
    }

    private void inicio() {
        tvCamara = findViewById(R.id.tvCamara);
        tvContactos = findViewById(R.id.tvContactos);
        tvAlmacenamiento = findViewById(R.id.tvAlmacenamiento);
        btnCheckPermissions = findViewById(R.id.btnCheckPermissions);
        btnRequestPermissions = findViewById(R.id.btnRequestPermissions);
    }

    private void verificarPermisos(View view) {

        // 0 ototago, -1 denegado
        switch (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            case 0:
                tvCamara.setText("Permisos de camara: activo");
                break;
            case -1:
                tvCamara.setText("Permisos de camara: inactivo");
                break;
        }

        switch (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)) {
            case 0:
                tvContactos.setText("Permisos de Contactos: activo");
                break;
            case -1:
                tvContactos.setText("Permisos de Contactos: inactivo");
                break;
        }

        switch (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            case 0:
                tvAlmacenamiento.setText("Permisos de Almacenamiento: activo");
                break;
            case -1:
                tvAlmacenamiento.setText("Permisos de Almacenamiento: inactivo");
                break;
        }
    }

    public void solicitarPermisos(View view) {
        if (btnRequestPermissions.isEnabled()) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void statusBtnRequestPermissions() {
        int statusCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int statusContactos = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int statusAlmacenamiento = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (statusCamara == PackageManager.PERMISSION_GRANTED &&
                statusContactos == PackageManager.PERMISSION_GRANTED &&
                statusAlmacenamiento == PackageManager.PERMISSION_GRANTED) {
            btnRequestPermissions.setEnabled(false);
        } else {
            btnRequestPermissions.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISO DENEGADO: " + permissions[i], Toast.LENGTH_SHORT).show();
                }
            }
            statusBtnRequestPermissions();
        }
    }
}

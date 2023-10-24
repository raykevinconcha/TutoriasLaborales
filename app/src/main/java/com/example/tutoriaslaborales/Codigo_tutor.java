package com.example.tutoriaslaborales;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Codigo_tutor extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_tutor);

        Button descarga = findViewById(R.id.descarga);
        descarga.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "lista.txt");
            activityForResultLauncher.launch(intent);

        });
    }

    ActivityResultLauncher<Intent> activityForResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null) {
                        try (ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(data.getData(), "w");
                             FileWriter fileWriter = new FileWriter(pfd.getFileDescriptor())) {
                            EditText texto = findViewById(R.id.codigo);
                            String textoGuardar = texto.getText().toString();
                            fileWriter.write("ID del tutor"+textoGuardar+
                                    "\n Empleados que son manager:"+
                                    "\n Jorge Benavides" +
                                    "\n Joaquin Carhuaz"+
                                    "\n Alvaro Calle");
                            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

}
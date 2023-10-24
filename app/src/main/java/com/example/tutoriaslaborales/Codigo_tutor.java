package com.example.tutoriaslaborales;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
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
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_tutor);

        Button descarga = findViewById(R.id.descarga);
        descarga.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(Codigo_tutor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Si no lo tienes, solicita el permiso
                ActivityCompat.requestPermissions(Codigo_tutor.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            } else {
                // Si ya tienes el permiso, puedes crear y escribir en el archivo
                crearYEsribirArchivo();
            }
        });
    }

    private void crearYEsribirArchivo() {
        // Define el nombre del archivo y la ruta
        String fileName = "mi_archivo.txt";
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + fileName;

        // Crea un objeto File
        File file = new File(filePath);

        try {
            // Crea el archivo si no existe
            if (!file.exists()) {
                file.createNewFile();
            }

            // Abre un FileWriter para escribir en el archivo
            FileWriter writer = new FileWriter(file);
            EditText editText = findViewById(R.id.codigo); // Obtén una referencia al EditText

            String valorIngresado = editText.getText().toString();
            // Escribe el contenido en el archivo
            writer.write(valorIngresado);

            // Cierra el FileWriter
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, crea y escribe en el archivo
                crearYEsribirArchivo();
            } else {
                Toast.makeText(Codigo_tutor.this, "no tienes permiso de almanacenamiento", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
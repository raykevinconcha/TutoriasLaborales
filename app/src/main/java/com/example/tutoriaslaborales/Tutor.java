package com.example.tutoriaslaborales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Tutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        ImageButton descarga = findViewById(R.id.imagedescargalistadetrabajadores);
        ImageButton asignar = findViewById(R.id.imageasignartutoria);
        descarga.setOnClickListener(view -> {
            Intent intent = new Intent(Tutor.this, Codigo_tutor.class);
            startActivity(intent);
        });
        asignar.setOnClickListener(view -> {
            Intent intent = new Intent(Tutor.this, Asignar.class);
            startActivity(intent);
        });
    }
}
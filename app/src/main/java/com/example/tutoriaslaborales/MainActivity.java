package com.example.tutoriaslaborales;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    String canal1 = "importanteHigh";
    String canal3 = "importanteHigh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton tutorButton = findViewById(R.id.imagetutor);
        ImageButton trabajadorButton = findViewById(R.id.imagetrabajador);
        crearCanalesNotificacion();

        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificarImportanceHigh();
                Intent intent = new Intent(MainActivity.this, Tutor.class);
                startActivity(intent);

            }
        });

        trabajadorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificartrabajador();
                Intent intent = new Intent(MainActivity.this, Trabajador.class);
                startActivity(intent);
            }
        });
    }

    public void crearCanalesNotificacion() {

        NotificationChannel channel = null;
        NotificationChannel channeltrabajador = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(canal1,
                    "Canal notificaciones high",
                    NotificationManager.IMPORTANCE_HIGH);
            channeltrabajador = new NotificationChannel(canal3,
                    "Canal notificaciones high",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Canal para notificaciones con prioridad high");
            channeltrabajador.setDescription("Canal para notificaciones con prioridad high");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channeltrabajador);
        }
        pedirPermisos();
    }
    public void pedirPermisos() {
        // TIRAMISU = 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }

    public void notificarImportanceHigh(){

        //Crear notificación
        Intent intent = new Intent(this, Tutor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal1)
                .setSmallIcon(R.drawable.tutor)
                .setContentTitle("Está entrando en modo Tutor")
                .setContentText("Aqui tendras 3 opciones")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        //Lanzar notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification);
        }

    }

    public void notificartrabajador(){

        //Crear notificación
        Intent intent = new Intent(this, Tutor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal3)
                .setSmallIcon(R.drawable.estudiante)
                .setContentTitle("Está entrando en modo Empleado")
                .setContentText("nada")  //esvalua si tiene agendada y cambia el texto
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        //Lanzar notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification);
        }

    }
}
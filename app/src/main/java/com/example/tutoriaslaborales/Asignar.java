package com.example.tutoriaslaborales;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Asignar extends AppCompatActivity {
    String canal2 = "importantedefault";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar);
        crearCanalesNotificacion();
        Button valid = findViewById(R.id.valida);
        valid.setOnClickListener(view -> {
            EditText codigo = findViewById(R.id.codigoe);
            EditText codigo1 = findViewById(R.id.codigoe2);
            String codigoText = codigo.getText().toString();
            String codigo1Text = codigo1.getText().toString();
            if (!codigoText.isEmpty() && !codigo1Text.isEmpty()) {
                int a = Integer.parseInt(codigoText);
                int b = Integer.parseInt(codigo1Text);
                if(a > b){ //colaca las validaciones aqui
                    notificarImportanceDefault();  //fijate abajo cual noti es para cual
                }else{
                    notificarImportanceDefault1();
                }
            }
        });
    }

   public void crearCanalesNotificacion() {

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(canal2,
                    "Canal notificaciones default",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription("Canal para notificaciones con prioridad default");
        }

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        pedirPermisos();
    }
    public void pedirPermisos() {
        // TIRAMISU = 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(Asignar.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }

    public void notificarImportanceDefault(){

        //Crear notificación
        Intent intent = new Intent(this, Tutor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal2)
                .setSmallIcon(R.drawable.tutor)
                .setContentTitle("Asignación del trabajador correcta")
                .setContentText("Asignación del trabajador correcta")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification);
        }

    }

    public void notificarImportanceDefault1(){

        //Crear notificación
        Intent intent = new Intent(this, Tutor.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal2)
                .setSmallIcon(R.drawable.tutor)
                .setContentTitle("Pii")
                .setContentText("El trabajador " +
                        "ya tiene una cita asignada. Elija otro trabajador")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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
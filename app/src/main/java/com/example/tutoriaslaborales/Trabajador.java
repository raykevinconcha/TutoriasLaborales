package com.example.tutoriaslaborales;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class Trabajador extends AppCompatActivity {
    String canal = "importante";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajador);
        crearCanalesNotificacion();
        ImageButton descarga = findViewById(R.id.imagedescargarhorarios);
        descarga.setOnClickListener(view -> {
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            notificar();
            // >=29
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                descargarConDownloadManager();
            }else{
                launcher.launch(permission);
            }
                }
        );
    }

    public void descargarConDownloadManager(){
        String fileName = "cita.jpg";
        String url = "https://i.pinimg.com/564x/4e/8e/a5/4e8ea537c896aa277e6449bdca6c45da.jpg";

        //URL URi
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(fileName);
        request.setMimeType("image/jpeg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + fileName);

        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.enqueue(request);
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    ActivityResultLauncher<String> launcher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {

                if (isGranted) { // permiso concedido
                    descargarConDownloadManager();
                } else {
                    Toast.makeText(Trabajador.this, "Este es un mensaje Toast", Toast.LENGTH_SHORT).show();
                }
            });


    public void crearCanalesNotificacion() {

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(canal,
                    "Canal notificaciones normal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad normal");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        pedirPermisos();
    }

    public void pedirPermisos() {
        // TIRAMISU = 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(Trabajador.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }

    public void notificar(){

        //Crear notificación
        Intent intent = new Intent(this, Trabajador.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal)
                .setSmallIcon(R.drawable.tutor)
                .setContentTitle("No cuenta con tutorías pendientes")
                .setContentText("Se ha descargado la imagen con los horarios")
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
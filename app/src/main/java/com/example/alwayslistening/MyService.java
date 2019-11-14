/*
 * PROYECTO:  AlwaysListening
 * PROGRAMA: Clase MyService
 * DESCRIPCIÓN: Service implementado para recibir el sonido permanentemente incluso cuando esta cerrada la aplicación.
 * PROGRAMADORES:
 *       -Sebastián González - 18588
 *       -Pablo Marroquín - 19077
 *       -Jorge Lara - 19449
 *       -María Paula Valdés - 19146
 *ULTIMA MODIFICACIÓN: 26/09/2019
 * AJUSTES PENDIENTES:
 *       -Implementar nuevos métodos activados desde esta clase para reconocimiento de voz
 *       -Validaciones de servicio activado
 * */

package com.example.alwayslistening;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static java.lang.Thread.sleep;

public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // crearemos un hilo para poder  trabajar el proceso que es tan pesado
        // ya que si se corre solo así entonces se cierra la app
        // NO ES  problema de que no se puedan llamar cosas al main, porque ya lo probe
        // y si funciona
        //prueba:
        /*
        boolean a  = true;

        MainActivity main = new MainActivity();
        main.prueba(a);
        */

        MainActivity main = new MainActivity();
      //  main.startThread();






        Toast.makeText(this,"Service Started", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // show message in screen
        Toast.makeText(this,"Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // a continuacion los threads



}

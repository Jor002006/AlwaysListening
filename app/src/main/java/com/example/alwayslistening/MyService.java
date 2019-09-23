package com.example.alwayslistening;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this,"Service Started", Toast.LENGTH_SHORT).show();


        ///////////// aquiiiiii poner el codigo que se va a ejecutrar entre el Toast.makeText(this,"Service Started", Toast.LENGTH_SHORT).show();
        //////////// y el return super.onStartCommand(intent, flags, startId);

        // creo que tambien se va a tener que pasar como parametros una lista con las palabras guardadas, tomar en cuenta

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
}

package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button NuevaPalabra;
    Button MisPalabras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NuevaPalabra = (Button) findViewById(R.id.nueva_palabra);
        MisPalabras = (Button) findViewById(R.id.mis_palabras);

        NuevaPalabra.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AbrirAgregarPantalla();
            }
        });
    }

    public void AbrirAgregarPantalla ()
    {
        Intent intent = new Intent(this, AgregarPalabra.class);
        startActivity(intent);
    }


    public void start(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    public void stop(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);


    }

    public void DameClick(View view) {


    }
}

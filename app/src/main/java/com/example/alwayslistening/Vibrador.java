package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class Vibrador extends AppCompatActivity {

    Button activar, desactivar;
    Vibrator vibrator;
    String[] Personas;
    int[] Vibraciones;
    String Nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrador);

        /*activar = (Button) findViewById(R.id.Activar);
        desactivar = (Button) findViewById(R.id.Desactivar);*/

        Personas = new String[] {"Juan","Luis","Lucho"};
        Vibraciones = new int[] {50,500,350};

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nombre = "Luis";
                Vibrar(Nombre, Personas, Vibraciones);
            }
        });

        desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nombre = "Jimmy";
                Vibrar(Nombre,Personas, Vibraciones);
            }
        });
    }

    public void Vibrar(String Nombre, String[] LisNom, int[] LisVi){

        for(int i = 0; i < LisNom.length; i++){
            if(Nombre.equalsIgnoreCase(LisNom[i])){
                vibrator.vibrate(LisVi[i]);
            }
        }
    }
}

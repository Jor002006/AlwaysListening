/*
 * PROYECTO:  AlwaysListening
 * PROGRAMA: Clase AgregarPalabras
 * DESCRIPCIÓN: Clase que provee las funcionalidades para insertar palabras en base de datos local.
 * PROGRAMADORES:
 *       -Sebastián González - 18588
 *       -Pablo Marroquín - 19077
 *       -Jorge Lara - 19449
 *       -María Paula Valdés - 19146
 *ULTIMA MODIFICACIÓN: 26/09/2019
 * AJUSTES PENDIENTES:
 *       -Revisar estética de interfaz
 * */

package com.example.alwayslistening;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//package com.example.apptimesince;

public class AgregarPalabra extends AppCompatActivity {

    private ImageButton prueba;
    private Button prueba2;
    private long contador;
    private Vibrator vibrator;
    long tiempoEntreApachado;
    boolean i =false;
    long bootTime;
    ArrayList<Long> sucesionSonidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_palabra);

        final ImageButton prueba = (ImageButton) findViewById(R.id.prueba);
        //final TextView timeText = (TextView) findViewById(R.id.timeText);
        bootTime = SystemClock.elapsedRealtime();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        prueba2 = (Button) (findViewById(R.id.button2));

        prueba2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast cont = Toast.makeText(getApplicationContext(), Long.toString(contador), Toast.LENGTH_SHORT);
               cont.show();
           }
        });

        prueba.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Long timeElapsed = SystemClock.elapsedRealtime();


                if (i) {
                    sucesionSonidos.add(contador);
                    Long timeElapsed2= SystemClock.elapsedRealtime();
                    Long timeElapsed3 = timeElapsed2- bootTime;
                    bootTime = timeElapsed2;
                    //timeText.setText(String.valueOf(timeElapsed3));
                    sucesionSonidos.add(timeElapsed3);
                    i = false;


                }
                else {

                    prueba();
                    i = true;

                }

            }
        });

    }



    //función que guarda el texto correspondiente a la palabra con su respectiva vibración en la base de datos.
    public void GuardarPalabra(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        EditText editText1 = (EditText)(findViewById(R.id.textBox1));

        Palabra palabra = new Palabra(new Random().nextInt(), editText1.getText().toString(), 1, 1000);
        //Alternativa de pruebas de INSERT
        //String insert="INSERT INTO Palabra (idPalabra, textoPalabra, activada, patronVibracion) values(123,'"+palabra.getTextoPalabra()+"',1, 1000)";

        String insert="INSERT INTO Palabra (idPalabra, textoPalabra, activada, patronVibracion) values" +
                "("+palabra.getIdPalabra()+
                ",'"+palabra.getTextoPalabra()+
                "',"+palabra.getActivada()+
                ", "+palabra.getPatronVibracion()+")";
        db.execSQL(insert);
        if(PalabraSiEsta(palabra.getTextoPalabra()))
        {
            Dialogo(palabra.getTextoPalabra().toUpperCase());
        }
        db.close();
    }

    // función que pregunta al usuario si está seguro de guardar su palabra.
    public void Dialogo(final String palabra)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("mis palabras");
        dialogo1.setMessage("¿Desea agregar ["+palabra+"] a su lista ");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Confirmacion(palabra+" agregada a Mis Palabras");
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Confirmacion("Cancelado");
            }
        });
        dialogo1.show();
    }

    // función que le muestra al usuario si su registro de palabra fue exitoso.
    void Confirmacion(String palabra)
    {
        Toast t=Toast.makeText(this,palabra, Toast.LENGTH_SHORT);
        t.show();
    }

    // función quere revisa si la palabra fue insertada correctamente en la base de datos.
    boolean PalabraSiEsta(String palabra)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra};
        boolean respuesta=false;

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM Palabra WHERE textoPalabra=? ",parametros);

            cursor.moveToFirst();
            if(cursor.getCount()>0)
            {respuesta=true;}


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }

    private void prueba(){
        prueba = (ImageButton) findViewById(R.id.prueba);

        prueba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    contador = System.currentTimeMillis();
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    contador = System.currentTimeMillis() - contador;
                }
                return true;
            }

        });

    }


}

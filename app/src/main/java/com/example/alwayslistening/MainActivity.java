/*
* PROYECTO:  AlwaysListening
* PROGRAMA: Clase MainActivity
* DESCRIPCIÓN: Clase primaria que inicializa el programa y redirige a otras vistas
* PROGRAMADORES:
*       -Sebastián González - 18588
*       -Pablo Marroquín - 19077
*       -Jorge Lara - 19449
*       -María Paula Valdés - 19146
*ULTIMA MODIFICACIÓN: 26/09/2019
* AJUSTES PENDIENTES:
*       -Implementar las funcionalidades del SERVICE para ser activado
*       -Reacomodar métodos para darle una funcionalidad de CONTROLADOR (MVC)
*       -Revisar estética de interfaz
* */

package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    Button NuevaPalabra;
    Button MisPalabrasButton;
    Button btnVoice;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NuevaPalabra = (Button) findViewById(R.id.nueva_palabra);
        MisPalabrasButton = (Button) findViewById(R.id.mis_palabras);


        NuevaPalabra.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AbrirAgregarPantalla();
            }
        });

        MisPalabrasButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AbrirMisPalabras();
            }
        });

        inicializarReconocimiento();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    //abren la pantalla correspondiente a la clase que se desea trabajar.
    public void AbrirAgregarPantalla ()
    {
        Intent intent = new Intent(this, AgregarPalabra.class);
        startActivity(intent);
    }

    //abren la pantalla correspondiente a la clase que se desea trabajar.
    public void AbrirMisPalabras ()
    {
        Intent intent = new Intent(this, MisPalabras.class);
        startActivity(intent);
    }


    //inician y detienen la ejecución del servicio para tareas en segundo plano.
    public void start(View view) {
        //Intent intent = new Intent(MainActivity.this, MyService.class);  //// Solo estan comentadas temporalmente para revision
        //startThread(View view);
        //startService(intent); //// Solo estan comentadas temporalmente para revision
        Toast.makeText(getApplicationContext(),"Servicio activado",Toast.LENGTH_LONG).show();

       /* Speechtext s = new Speechtext();
        s.mostrarAudioInput();*/
    }

    //inician y detienen la ejecución del servicio para tareas en segundo plano.
    public void stop(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);


    }

    public void DameClick(View view) {


    }

    //método que habilita las funcionalidades de Google para reconocimiento de voz y traducir a texto.
     private void inicializarReconocimiento(){

        btnVoice = (Button) findViewById(R.id.btnVoice);

        PackageManager pm = getPackageManager();


        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        if(activities.size() != 0){
            btnVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startVoiceRecognitionActivity();
                }
            });
        }
    }

    //método que habilita las funcionalidades de Google para reconocimiento de voz y traducir a texto.
    private void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    //habilitan funciones de reconocimiento de voz.
    public void startThread() {
        VoiceRecognitionThread thread = new VoiceRecognitionThread();
        thread.run();

    }

    //habilitan funciones de reconocimiento de voz.
    public void stopThread(View view) {

    }

    //habilitan funciones de reconocimiento de voz.
    class VoiceRecognitionThread extends Thread {

        @Override
        public void run() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final CharSequence[] items = new CharSequence[matches.size()];
            for(int i = 0; i < matches.size(); i++){
                items[i] = matches.get(i);
            }
            Toast yo = Toast.makeText(getApplicationContext(), items[0], Toast.LENGTH_LONG);
            yo.show();
            //Aqui lo que ocurre es que el servicio de google tiene una lista de palabras/frase detectadas ordenadas de mayor confianza a menor confianza

            String palabra;
            palabra = items[0].toString();
            Toast tosty = Toast.makeText(getApplicationContext(), palabra, Toast.LENGTH_LONG);
            tosty.show();
            //Aqui se escoge el primer elemento de las palabras detectadas porque es el que tiene mayor confianza y se convierte en un String para realizar la comparacion

            if(palabra.equalsIgnoreCase("cuidado" ) || PalabraSiEsta(palabra)){
                /*final long[] pattern = {0, 2000, 500, 500};
                int tiempo = (int)((int)pattern[0]+(int)pattern[1]+(int)pattern[2]+(int)pattern[3])/(int)1000;
                for(int i = 0; i < 5; i++){
                    vibrator.vibrate(pattern,  0);
                    try {
                        TimeUnit.SECONDS.sleep(tiempo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                vibrator.cancel();*/
                long[] pattern =TraerPatron(palabra);

                int tiempo = 0;
                for(int i=0;i<pattern.length;i++)
                {
                    tiempo+=(int)pattern[i];
                }
                tiempo=tiempo/1000;

               // for(int i = 0; i < 5; i++){
                    vibrator.vibrate(pattern,  0);
                    try {
                        TimeUnit.SECONDS.sleep(tiempo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}




            }
            //Esto hace la comparación de la palabra detectada.
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*private long[]*/ long[] TraerPatron(String palabra)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, com.example.alwayslistening.utilidades.NOMBRE_BD, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra};
        String patronString="";

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT patronVibracion  FROM "+com.example.alwayslistening.utilidades.TABLA_PALABRA+" WHERE textoPalabra=? ",parametros);
            cursor.moveToFirst();
            patronString = cursor.getString(0);
           // Toast.makeText(getApplicationContext(),patronString,Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }


        int cantVibraciones = patronString.split(",").length;
        long[] patron = new long[cantVibraciones];
        String[] valores =patronString.split(",");

        int size = valores.length;
        for(int i=0; i<size; i++) {
            String valor=valores[i];
            patron[i] = (long)Integer.parseInt(valor);
        }

        return patron;
    }

    //función para verificar si el audio escuchado corresponde a una palabra de la base de datos. (temporalmente)
    boolean PalabraSiEsta(String palabra)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, com.example.alwayslistening.utilidades.NOMBRE_BD, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra};
        boolean respuesta=false;

        try {
            //select nombre,telefono from usuario where codigo=?
            //Cursor cursor=db.rawQuery("SELECT * FROM "+com.example.alwayslistening.utilidades.TABLA_PALABRA+" WHERE textoPalabra=? ",parametros);
            Cursor cursor=db.rawQuery("SELECT * FROM "+com.example.alwayslistening.utilidades.TABLA_PALABRA+" WHERE textoPalabra= '"+palabra+"' AND activada = 1",null);
            cursor.moveToFirst();
            if(cursor.getCount()>0)
            {respuesta=true;}


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }




    /// $$$$$$$$$$$$$$$$$$$$44 PRUEBAS $$$$$$$$$$$$$$$$$$$$$$$$
    public void prueba (boolean a) {
        System.out.println(a);

    }

}

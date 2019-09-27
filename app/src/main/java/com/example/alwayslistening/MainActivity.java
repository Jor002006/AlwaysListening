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
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    Button NuevaPalabra;
    Button MisPalabras;
    Button btnVoice;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NuevaPalabra = (Button) findViewById(R.id.nueva_palabra);
        MisPalabras = (Button) findViewById(R.id.mis_palabras);

        inicializarReconocimiento();


        NuevaPalabra.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AbrirAgregarPantalla();
            }
        });
        //inicializarReconocimiento();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

   /* public ArrayList<String> MiLista()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        ArrayList<String> lista = new ArrayList<String>();

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM Palabra",null);

            cursor.moveToFirst();
            if(cursor.getCount()>0)
                for(int i=0;i<cursor.getCount();i++)
                {
                    lista.add( cursor.getString(cursor.getColumnIndex("textoPalabra")) );
                }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return lista;
    }*/

    public void AbrirAgregarPantalla ()
    {
        Intent intent = new Intent(this, AgregarPalabra.class);
        startActivity(intent);
    }


    public void start(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        //startThread(View view);
        startService(intent);

       /* Speechtext s = new Speechtext();
        s.mostrarAudioInput();*/
    }

    public void stop(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);


    }

    public void DameClick(View view) {


    }

    //Metodo pa´ reconocer la voz
    private void inicializarReconocimiento(){

        btnVoice = (Button) findViewById(R.id.btnVoice);

        PackageManager pm = getPackageManager();


        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        //Aqui se buscan los paquetes necesarios

        if(activities.size() != 0){
            btnVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Se llama al metodo para iniciar el reconocimiento de voz
                    startVoiceRecognitionActivity();
                }
            });
        }
    }

    //Se utiliza el servicio de google para reconocer la voz
    private void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public void startThread() {
        VoiceRecognitionThread thread = new VoiceRecognitionThread();
        thread.run();
        //pruebaaaaaaa
    }

    public void stopThread(View view) {

    }

    class VoiceRecognitionThread extends Thread {

        @Override
        public void run() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        }
    }


    //Se reemplaza uno de los metodos del reconocerdor de voz para que haga lo que queremos
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
            //Aqui lo que ocurre es que el servicio de google crea una lista de palabra reconocidas en orden de mayor confianza a menor

            String palabra;
            palabra = items[0].toString();
            //Aqui se escoge el primer match de los resultados del reconocimiento porque es el más confiable

            Toast tosty = Toast.makeText(getApplicationContext(), palabra, Toast.LENGTH_LONG);
            tosty.show();
            if(palabra.equalsIgnoreCase("cuidado" ) || PalabraSiEsta(palabra)){
                vibrator.vibrate(500);
            }
            //Esto hace la comparación de la palabra y hace la vibracion.
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    boolean PalabraSiEsta(String palabra)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra.toLowerCase()};
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




    /// $$$$$$$$$$$$$$$$$$$$44 PRUEBAS $$$$$$$$$$$$$$$$$$$$$$$$
    public void prueba (boolean a) {
        System.out.println(a);

    }

}

package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    Button NuevaPalabra;
    Button MisPalabras;
    Button btnVoice;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

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
        inicializarReconocimiento();
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

    //Metodo pa´ reconocer la voz por si las moscas
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

    private void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
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
            String palabra;
            palabra = items[0].toString();
            Toast tosty = Toast.makeText(getApplicationContext(), palabra, Toast.LENGTH_LONG);
            tosty.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

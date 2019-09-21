package com.example.alwayslistening;
import android.database.sqlite.SQLiteDatabase;
import android.content.ActivityNotFoundException;
import android.os.Vibrator;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Locale;//?

import android.widget.Toast;




public class Speechtext extends AppCompatActivity {
    private ArrayList<String> palabrarecibida;


    public void mostrarAudioInput() {// metodo para que cuando reconozca la palabra la muestre en pantalla.
        Intent mod = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //reconoce idioma en que se habla
        mod.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mod.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(mod, 100);
        } catch (ActivityNotFoundException que) {

            Toast.makeText(Speechtext.this, "Oh no", Toast.LENGTH_LONG).show();//Si no reconoce el texto muestra este mensaje
        }
    }

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

    int TomarVibracion(String palabra)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra};
        int respuesta=0;

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM Palabra WHERE textoPalabra=? ",parametros);

            cursor.moveToFirst();
            respuesta = cursor.getInt(cursor.getColumnIndex("patronVibracion"));


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }

    public void onActivityResult(int request_code, int result_code, Intent mod) {//metodo que guarda la informacion que es captada por el dispositivo.
        super.onActivityResult(request_code, result_code, mod);// request code ya esta determinado en el metodo anterior
        //result code es si en verdad capto una palabra y la reconoce, y mod es el objeto que reconocio el audio.(metodo anterior)
        if (request_code==100) {
            if (result_code == RESULT_OK /*&& !=null*/){  // Result_ok = true
                ArrayList<String> palabrarecibida = mod.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(PalabraSiEsta(palabrarecibida.toString()))
                {
                   Vibrator vibrator;
                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(TomarVibracion(palabrarecibida.toString()));
                }
            }
        }
    }

}
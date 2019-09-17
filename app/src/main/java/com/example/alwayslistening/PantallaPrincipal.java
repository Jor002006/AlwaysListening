package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactos.utilidades.utilidades;

public class PantallaPrincipal extends AppCompatActivity {

    EditText Texto, Vibracion;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        /*Texto = (EditText) findViewById(R.id.editText1);
        Vibracion = (EditText) findViewById(R.id.editText2);
    }*/

    private void NuevaPalabra()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_ID, 1/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_TEXTO, Texto.getText().toString()/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_ACTIVADA, 1/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_PATRON_VIBRACION, Vibracion.getText().toString()/*Valor del textBox asignado*/);

        Long registros = db.insert(utilidades.TABLA_PALABRA, utilidades.CAMPO_ID, values);
        Toast.makeText(getApplicationContext(), "Palabras: "+registros,Toast.LENGTH_SHORT).show();
    }
}

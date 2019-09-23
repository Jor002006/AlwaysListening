package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AgregarPalabra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_palabra);
    }

    public void GuardarPalabra(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

       // ContentValues values = new ContentValues();
       // values.put(com.example.alwayslistening.utilidades.CAMPO_ID, 1/*Valor del textBox asignado*/);
       // values.put(com.example.alwayslistening.utilidades.CAMPO_TEXTO, Texto.getText().toString()/*Valor del textBox asignado*/);
       // values.put(com.example.alwayslistening.utilidades.CAMPO_ACTIVADA, 1/*Valor del textBox asignado*/);
       // values.put(com.example.alwayslistening.utilidades.CAMPO_PATRON_VIBRACION, Vibracion.getText().toString()/*Valor del textBox asignado*/);

       // Long registros = db.insert(com.example.alwayslistening.utilidades.TABLA_PALABRA, com.example.alwayslistening.utilidades.CAMPO_ID, values);
       // String _texto = Texto.getText().toString();
       // Toast.makeText(getApplicationContext(), "Palabra: '"+_texto+"' guardada. "+registros.toString()+" palabras en total.",Toast.LENGTH_SHORT).show();
       // db.close();
    }
}

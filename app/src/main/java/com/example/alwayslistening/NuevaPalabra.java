package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alwayslistening.utilidades;

public class NuevaPalabra extends AppCompatActivity {

    EditText Texto, Vibracion;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_palabra);

        Texto = (EditText) findViewById(R.id.editText1);
        Vibracion = (EditText) findViewById(R.id.editText2);
    }*/

    public void AbrirPantalla (View view){
        Intent anadir = new Intent(this, Anadir.class);
        startActivity(anadir);
    }

    public void GuardarPalabra(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_ID, 1/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_TEXTO, Texto.getText().toString()/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_ACTIVADA, 1/*Valor del textBox asignado*/);
        values.put(utilidades.CAMPO_PATRON_VIBRACION, Vibracion.getText().toString()/*Valor del textBox asignado*/);

        Long registros = db.insert(utilidades.TABLA_PALABRA, utilidades.CAMPO_ID, values);
        String _texto = Texto.getText().toString();
        Toast.makeText(getApplicationContext(), "Palabra: '"+_texto+"' guardada. "+registros.toString()+" palabras en total.",Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void GuardarPalabra2()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String insert="INSERT INTO Palabra (idPalabra, textoPalabra, activada, patronVibracion) values(123,'hola',1, 1000)";
        db.execSQL(insert);
        db.close();
    }

    /*public ArrayList getAllWords() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        //SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "select * from "+CONTACTS_TABLE_NAME, null );
        res.moveToFirst();
        while(res.isAfterLast() = = false) {
            array_list.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }
        return array_list;
    }*/

}

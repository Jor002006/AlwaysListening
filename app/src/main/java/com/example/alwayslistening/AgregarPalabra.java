package com.example.alwayslistening;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    public void GuardarPalabra2(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        EditText palabra = (EditText)(findViewById(R.id.textBox1));
        String texto_Palabra = palabra.getText().toString();
        String insert="INSERT INTO Palabra (idPalabra, textoPalabra, activada, patronVibracion) values(123,'"+texto_Palabra+"',1, 1000)";
        db.execSQL(insert);
        if(PalabraSiEsta(texto_Palabra))
        {

            Toast t=Toast.makeText(this,"Se agrego la palabra"+texto_Palabra, Toast.LENGTH_SHORT);
            t.show();
        }
        db.close();
    }

    public void Dialogo(View view)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Acepta la ejecución de este programa en modo prueba ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void aceptar() {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {
        finish();
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

    public String PalabrasQueHay()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "BaseDeDatos", null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String respuesta="";

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM Palabra",null);

            cursor.moveToFirst();
            if(cursor.getCount()>0)
            for(int i=0;i<cursor.getCount();i++)
            {
                respuesta+=cursor.getString(cursor.getColumnIndex("textoPalabra"));
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }
}

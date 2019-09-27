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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class AgregarPalabra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_palabra);
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


}

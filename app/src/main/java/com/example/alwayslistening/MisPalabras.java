/*
 * PROYECTO:  AlwaysListening
 * PROGRAMA: Clase MisPalabras
 * DESCRIPCIÓN: Pantalla con las funcionalidades para administrar las configuraciones de las palabras guardadas.
 * PROGRAMADORES:
 *       -Sebastián González - 18588
 *       -Pablo Marroquín - 19077
 *       -Jorge Lara - 19449
 *       -María Paula Valdés - 19146
 *ULTIMA MODIFICACIÓN: 26/09/2019
 * AJUSTES PENDIENTES:
 *       -Implementar las funcionalidades para activar, desactivar o eliminar palabras
 *       -Implementar funcionalidades para modificar vibraciones correspondientes a palabras
 *       -Revisar estética de interfaz
 * */


package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MisPalabras extends AppCompatActivity {


    ListView LV;
    ArrayList<String> listaInformacion;
    ArrayList<Palabra> listaPalabras;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_palabras);

        conn = new ConexionSQLiteHelper(getApplicationContext(),com.example.alwayslistening.utilidades.NOMBRE_BD,null, 1);

        LV = (ListView) findViewById(R.id.lv1);
         consultarListaPalabras();

         ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
         LV.setAdapter(adaptador);

         LV.setOnItemClickListener(new AdapterView.OnItemClickListener()
             {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
                 {
                     String textoPalabra = listaPalabras.get(pos).getTextoPalabra()+"\n";
                     String estado=(listaPalabras.get(pos).getActivada()==1)?"Activada":"Silencjo";//listaPalabras.get(pos).getPatronVibracion();
                    MostrarPestana(textoPalabra,estado);

                 }
             }
         );
    }

    private void consultarListaPalabras()
    {
        SQLiteDatabase db = conn.getReadableDatabase();
        Palabra palabra = null;
        listaPalabras = new ArrayList<Palabra>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+com.example.alwayslistening.utilidades.TABLA_PALABRA, null);

        while(cursor.moveToNext())
        {
            palabra = new Palabra(cursor.getInt(0), cursor.getString(1),cursor.getInt(2), cursor.getString(3));
            listaPalabras.add(palabra);
        }

        obtenerListaInformacion();
    }

    private void obtenerListaInformacion()
    {
        listaInformacion = new ArrayList<String>();
        for(int i=0;i<listaPalabras.size();i++)
        {
            listaInformacion.add(listaPalabras.get(i).getTextoPalabra()+" ");
        }
    }

    //toma de la base de datos todas las palabras registradas.
    public String PalabrasQueHay()
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, com.example.alwayslistening.utilidades.NOMBRE_BD, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String respuesta="";

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+com.example.alwayslistening.utilidades.TABLA_PALABRA,null);

            cursor.moveToFirst();
            if(cursor.getCount()>0)
                for(int i=0;i<cursor.getCount();i++)
                {
                    respuesta+=cursor.getString(cursor.getColumnIndex("textoPalabra"))+"\n";
                    cursor.moveToNext();
                }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }

    //Muestra en pantalla todas las palabras registradas en la base de datos.
    public void MostrarPalabras(View view)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Mis Palabras");
        dialogo1.setMessage(PalabrasQueHay());
        dialogo1.show();
    }

    private void MostrarPestana(final String title, String msg)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(title);
        dialogo1.setMessage("ESTADO: "+msg);

        final String primerBoton=msg.equalsIgnoreCase("Activada")?"SILENCIAR":"ACTIVAR";
        dialogo1.setPositiveButton(primerBoton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String desplegado= primerBoton.equalsIgnoreCase("SILENCIAR")?"["+title+"] silenciada":"["+title+"] activada";
                    }
                });
                dialogo1.show();
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

    //función para eliminar una palabra del sistema.
    public void Borrar(String palabra)
    {
        //Pendiente
    }

    //función para deshabilitar temporalmente una palabra.
    public void SilenciarPalabra(String palabra)
    {
        //Pendiente
    }



}

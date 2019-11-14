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
import android.content.ContentValues;
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

//Variables globales par manipular de forma ordenada las vibraciones
    ListView LV;
    ArrayList<String> listaInformacion;
    ArrayList<Palabra> listaPalabras;
    ConexionSQLiteHelper conn;
    Palabra palabraActual=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_palabras);

        conn = new ConexionSQLiteHelper(getApplicationContext(),com.example.alwayslistening.utilidades.NOMBRE_BD,null, 1);

        LV = (ListView) findViewById(R.id.lv1);
         consultarListaPalabras();

         PonerAdapter();

         LV.setOnItemClickListener(new AdapterView.OnItemClickListener()
             {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
                 {
                     try { //Se revisa el estado actual de la palabra si esta activa o no para presentar las opciones al usuario
                         palabraActual = listaPalabras.get(pos);
                         String textoPalabra = listaPalabras.get(pos).getTextoPalabra();
                         String estado = (listaPalabras.get(pos).getActivada() == 1) ? "Activada" : "Silencio";//listaPalabras.get(pos).getPatronVibracion();
                         MostrarPestana(textoPalabra, estado);
                     }
                     catch(Exception e)
                     {
                         Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                     }

                 }
             }
         );
    }

    private void PonerAdapter()
    {
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        LV.setAdapter(adaptador);
    }

    //SELECT en la base de datos para traer la notificacion de cada palabra
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

    //Prepara el contenido de la lista de opciones que se despliegan en pantalla
    private void obtenerListaInformacion()
    {
        listaInformacion = new ArrayList<String>();
        for(int i=0;i<listaPalabras.size();i++)
        {
            String estado=(listaPalabras.get(i).getActivada()==0)?"SILENCIO":"ACTIVADA";
            listaInformacion.add(listaPalabras.get(i).getTextoPalabra()+"    ["+estado+"]");
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

    //Opciones para SILENCIAR y ACTIVAR palabras es aqui
    private void MostrarPestana( String title, String msg)
    {
        try{
        final boolean PalabraActivada=msg.equalsIgnoreCase("Activada");
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(title);
        dialogo1.setMessage("ESTADO: "+msg);

        final String primerBoton=(PalabraActivada)?"SILENCIAR":"ACTIVAR";
        dialogo1.setPositiveButton(primerBoton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            //dependiente del estado actual de la palabra eso es lo que se le asinga
                            String desplegado = (PalabraActivada) ? "[" + palabraActual.getTextoPalabra() + "] silenciada" : "[" + palabraActual.getTextoPalabra() + "] activada";
                            int nuevoEstado = (PalabraActivada) ? 0 : 1;
                            SQLiteDatabase db = conn.getReadableDatabase();
                            String[] parametros = {palabraActual.getTextoPalabra()};//title contiene la palabra
                            ContentValues cv = new ContentValues();
                            cv.put(com.example.alwayslistening.utilidades.CAMPO_ID, palabraActual.getIdPalabra());
                            cv.put(com.example.alwayslistening.utilidades.CAMPO_TEXTO, palabraActual.getTextoPalabra());
                            cv.put(com.example.alwayslistening.utilidades.CAMPO_ACTIVADA, nuevoEstado);
                            cv.put(com.example.alwayslistening.utilidades.CAMPO_PATRON_VIBRACION, palabraActual.getPatronVibracion());

                            db.update(com.example.alwayslistening.utilidades.TABLA_PALABRA, cv, com.example.alwayslistening.utilidades.CAMPO_TEXTO + "='" + palabraActual.getTextoPalabra() + "'", null);
                            consultarListaPalabras();
                            PonerAdapter();
                            String mensajeToast = (PalabraActivada) ? "Palabra silenciada" : "Palabra activada";
                            Toast.makeText(getApplicationContext(), mensajeToast, Toast.LENGTH_LONG).show();
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

        dialogo1.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try{    //Metodo para elemininar palabras
                SQLiteDatabase db=conn.getReadableDatabase();
                String[] parametros={palabraActual.getTextoPalabra()};//title contiene la palabra
                db.delete(com.example.alwayslistening.utilidades.TABLA_PALABRA, com.example.alwayslistening.utilidades.CAMPO_TEXTO+"='"+palabraActual.getTextoPalabra()+"'", null);
                consultarListaPalabras();
                PonerAdapter();
                String mensajeToast="Palabra eliminada";
                Toast.makeText(getApplicationContext(),mensajeToast,Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
                dialogo1.show();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

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

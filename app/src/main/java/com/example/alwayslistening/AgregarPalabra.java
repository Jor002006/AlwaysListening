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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.widget.ListView;
//package com.example.apptimesince;

public class AgregarPalabra extends AppCompatActivity {

    //Variables globales de la clase
    private ImageButton prueba;
    private Button prueba2;
    private long contador;
    private Vibrator vibrator;
    long tiempoEntreApachado;
    boolean i =false;
    long bootTime;
    ArrayList<Long> sucesionSonidos;
    ListView LV;
    ArrayList<String> listaInformacion;
    ArrayList<long[]> listaVibraciones;
    long[] vibracionSeleccionada=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_palabra);

        //Preparacion de Listas y objetos especiales para manipular las vibraciones dentro del menu
        bootTime = SystemClock.elapsedRealtime();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sucesionSonidos = new ArrayList<Long>();
        LV = (ListView) findViewById(R.id.lv1);
        PrepararListaVibraciones();
        PonerAdapter();

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
                                      {
                                          try
                                          {
                                              //De acuerdo con la vibracion que se selecciono se toma su patron de la lista de vibraciones para insertarla en la BD
                                              vibracionSeleccionada=listaVibraciones.get(pos);
                                              long[] pattern =vibracionSeleccionada;
                                              //ConvertirPatronAString(pattern);

                                              int tiempo = 0;
                                              for(int i=0;i<pattern.length;i++)
                                              {
                                                  tiempo+=(int)pattern[i];
                                              }
                                              tiempo=tiempo/1000;

                                              // Metodo que provoca la vibracion como tal
                                              vibrator.vibrate(pattern,  -1);
                                              try {
                                                  TimeUnit.SECONDS.sleep(tiempo);
                                              } catch (InterruptedException e) {
                                                  e.printStackTrace();
                                              }
                                          }
                                          catch(Exception e)
                                          {
                                              Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                          }

                                      }
                                  }
        );

    }

    //El patron de vibracion se guarda como String entonces hay que tener funciones de conversion
    private String ConvertirPatronAString(long[] patron)
    {
        String resp="";
        for(int i=0;i<patron.length;i++)
        {
            resp+=patron[i]+",";
        }
        return resp;
    }

    public void MostrarPatron(View view)
    {
        String patronComoString = ConvertirPatronAString(vibracionSeleccionada);
        Toast.makeText(getApplicationContext(),patronComoString,Toast.LENGTH_LONG).show();
    }

    //Metodo principal encargado de insertar la palabra en la base de datos de SQLite
    public void GuardarPalabra(View view)
    {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, com.example.alwayslistening.utilidades.NOMBRE_BD, null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        EditText editText1 = (EditText)(findViewById(R.id.textBox1));

        boolean bandera=true;
        String mensaje="";

        //Validacion de texto vacio
        if(editText1.getText().toString().equalsIgnoreCase(""))
        {
            bandera=false;
            mensaje="Escriba una palabra";
        }

        //validacion de no seleccionar vibracion
        if(vibracionSeleccionada==null)
        {
            bandera=false;
            mensaje="Seleccione una vibración";
        }

        //Procedimiento para insertar en la base de datos si se aprobaron todas las validaciones
        if(bandera)
        {

            String patronComoString = ConvertirPatronAString(vibracionSeleccionada);
            Palabra palabra = new Palabra(new Random().nextInt(), editText1.getText().toString(), 1, patronComoString);
            //Alternativa de pruebas de INSERT
            //String insert="INSERT INTO Palabra (idPalabra, textoPalabra, activada, patronVibracion) values(123,'"+palabra.getTextoPalabra()+"',1, 1000)";

            //Sentencia SQL para hacer el Insert
            String insert = "INSERT INTO " + com.example.alwayslistening.utilidades.TABLA_PALABRA + " (idPalabra, textoPalabra, activada, patronVibracion) values" +
                    "(" + palabra.getIdPalabra() +
                    ",'" + palabra.getTextoPalabra() +
                    "'," + palabra.getActivada() +
                    ", '" + palabra.getPatronVibracion() + "')";
            db.execSQL(insert);
            if (PalabraSiEsta(palabra.getTextoPalabra())) {
                Dialogo(palabra.getTextoPalabra().toUpperCase());
            }
        }
        else
        {
            Toast t=Toast.makeText(this,mensaje, Toast.LENGTH_SHORT);
            t.show();
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
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, com.example.alwayslistening.utilidades.NOMBRE_BD, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={palabra};
        boolean respuesta=false;

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT * FROM "+ com.example.alwayslistening.utilidades.TABLA_PALABRA+" WHERE textoPalabra=? ",parametros);

            cursor.moveToFirst();
            if(cursor.getCount()>0)
            {respuesta=true;}


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }

    //Se preparan los datos de la lista de posibles vibraciones con sus valores
    private void PrepararListaVibraciones()
    {

        listaInformacion= new ArrayList<String>();
        listaInformacion.add("Creciente simple"); //0
        listaInformacion.add("Creciente extensa"); //1
        listaInformacion.add("Decreciente simple"); //2
        listaInformacion.add("Decreciente extensa"); //3
        listaInformacion.add("Ráfaga simple"); //4
        listaInformacion.add("Ráfaga extensa"); //5
        listaInformacion.add("Continua simple"); //6
        listaInformacion.add("Continua extensa"); //7

        listaVibraciones = new ArrayList<long[]>();
        long[] v0 = {100,100,100, 200,100, 300,100, 400,100};
        listaVibraciones.add(v0);
        long[] v1 = {100,250,100, 500,100, 750,100, 1000,100};
        listaVibraciones.add(v1);
        long[] v2 = {100,400,100, 300,100, 200,100, 100,100};
        listaVibraciones.add(v2);
        long[] v3 = {100,1000,100, 750,100, 500,100, 250,100};
        listaVibraciones.add(v3);
        long[] v4 = {100,100,100, 100,100, 100,100, 100,100};
        listaVibraciones.add(v4);
        long[] v5 = {100,300,100, 300,100, 300,100, 300,100};
        listaVibraciones.add(v5);
        long[] v6 = {100,1000,100};
        listaVibraciones.add(v6);
        long[] v7 = {100,2000,100};
        listaVibraciones.add(v7);

    }

    //Se traslada la lista de vibraciones a la pantalla
    private void PonerAdapter()
    {
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        LV.setAdapter(adaptador);
    }

}

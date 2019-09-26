package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MisPalabras extends AppCompatActivity {


    ListView LV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_palabras);

        /*String[] foods ={"manzana", "pera", "banano"};
        ListAdapter LA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        LV = (ListView)(findViewById((R.id.ListView1)));
        LV.setAdapter(LA);*/
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
                    respuesta+=cursor.getString(cursor.getColumnIndex("textoPalabra"))+"\n";
                    cursor.moveToNext();
                }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();

        }
        return respuesta;
    }

    public void Dialogo()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Mis Palabras");
        dialogo1.setMessage(PalabrasQueHay());
        /*dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Confirmacion(palabra+" agregada a Mis Palabras");
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Confirmacion("Cancelado");
            }
        });*/
        dialogo1.show();
    }

    public void VerPalabras(View view)
    {
        Dialogo();
    }




}

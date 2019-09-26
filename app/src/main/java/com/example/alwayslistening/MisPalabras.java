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

    public void MostrarPalabras(View view)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Mis Palabras");
        dialogo1.setMessage(PalabrasQueHay());

        dialogo1.show();
    }

    /*public void VerPalabras(View view)
    {
        Dialogo();
    }*/

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

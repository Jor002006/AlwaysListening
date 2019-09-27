/*
 * PROYECTO:  AlwaysListening
 * PROGRAMA: Clase ConexionSQLiteHelper
 * DESCRIPCIÓN: Clase externa para tener las funcionalidades de manejo de bases de datos locales.
 * PROGRAMADORES:
 *       -Sebastián González - 18588
 *       -Pablo Marroquín - 19077
 *       -Jorge Lara - 19449
 *       -María Paula Valdés - 19146
 *ULTIMA MODIFICACIÓN: 26/09/2019
 * AJUSTES PENDIENTES:
 *       -Ninguno
 * */

package com.example.alwayslistening;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.alwayslistening.utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    //establece los parámetros iniciales de la base de datos.
    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //crea la base de datos con las tablas requeridas.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_PALABRA);
    }

    //si se requiere, elimina la base de datos.
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Palabra");
    }
}

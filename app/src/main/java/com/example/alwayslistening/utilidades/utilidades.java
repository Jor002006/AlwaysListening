package com.example.alwayslistening;

public class utilidades {

    //Constantes campos tablas palabra
    public static final String TABLA_PALABRA="Palabra1";
    public static final String CAMPO_ID="idPalabra";
    public static final String CAMPO_TEXTO="textoPalabra";
    public static final String CAMPO_ACTIVADA="activada";
    public static final String CAMPO_PATRON_VIBRACION="patronVibracion";
    public static final String NOMBRE_BD="BaseDeDatos1";

    public static final String CREAR_TABLA_PALABRA ="CREATE TABLE "+TABLA_PALABRA+" " +
            "("+CAMPO_ID+" INTEGER," +
            " "+CAMPO_TEXTO+" TEXT, "+
            CAMPO_ACTIVADA+" INTEGER, "+
            CAMPO_PATRON_VIBRACION+" TEXT)";


}

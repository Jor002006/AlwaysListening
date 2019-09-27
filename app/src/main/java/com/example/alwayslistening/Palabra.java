/*
 * PROYECTO:  AlwaysListening
 * PROGRAMA: Clase Palabra
 * DESCRIPCIÓN: Objeto que modela la palabra con sus propiedades características para manipulación en el sistema.
 * PROGRAMADORES:
 *       -Sebastián González - 18588
 *       -Pablo Marroquín - 19077
 *       -Jorge Lara - 19449
 *       -María Paula Valdés - 19146
 *ULTIMA MODIFICACIÓN: 26/09/2019
 * AJUSTES PENDIENTES:
 *       Ninguno.
 * */

package com.example.alwayslistening;

public class Palabra
{
    //Atributos del objeto palabra
    private int idPalabra;
    private String textoPalabra;
    private int  activada;
    private int patronVibracion;

    //constructor del objeto palabra donde se asignan sus propiedades iniciales en la instancia.
    public Palabra(int id, String texto,int activated, int patron)
    {
        idPalabra=id;
        textoPalabra=texto;
        activada=activated;
        patronVibracion=patron;
    }

    //funciones para leer los valores de las propiedades en el objeto preservando la encapsulación.
    public int getIdPalabra() {
        return idPalabra;
    }

    public int getPatronVibracion() {
        return patronVibracion;
    }

    public String getTextoPalabra() {
        return textoPalabra;
    }

    public int getActivada() {
        return activada;
    }

    //funciones para modificar los valores de las propiedades en el objeto preservando la encapsulación.
    public void setActivada(int activada) {
        this.activada = activada;
    }

    public void setIdPalabra(int idPalabra) {
        this.idPalabra = idPalabra;
    }

    public void setPatronVibracion(int patronVibracion) {
        this.patronVibracion = patronVibracion;
    }

    public void setTextoPalabra(String textoPalabra) {
        this.textoPalabra = textoPalabra;
    }
}

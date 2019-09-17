package com.example.alwayslistening;

public class Palabra
{
    private int idPalabra;
    private String textoPalabra;
    private int  activada;
    private int[] patronVibracion;

    //gg
    public int getIdPalabra() {
        return idPalabra;
    }

    public int[] getPatronVibracion() {
        return patronVibracion;
    }

    public String getTextoPalabra() {
        return textoPalabra;
    }

    public int isActivada() {
        return activada;
    }

    public void setActivada(int activada) {
        this.activada = activada;
    }

    public void setIdPalabra(int idPalabra) {
        this.idPalabra = idPalabra;
    }

    public void setPatronVibracion(int[] patronVibracion) {
        this.patronVibracion = patronVibracion;
    }

    public void setTextoPalabra(String textoPalabra) {
        this.textoPalabra = textoPalabra;
    }
}

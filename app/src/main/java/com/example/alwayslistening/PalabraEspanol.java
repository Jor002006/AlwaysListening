package com.example.alwayslistening;

public class PalabraEspanol extends Palabra {

    private String significado;

    public PalabraEspanol(int id, String texto,int activated, String patron)
    {
        super(id, texto, activated, patron);

    }

    public void setSignificado(String sig)
    {
        this.significado=sig;
    }

    public String getSignificado() {
        return significado;
    }
}

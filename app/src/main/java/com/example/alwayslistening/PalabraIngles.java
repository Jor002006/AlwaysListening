package com.example.alwayslistening;

public class PalabraIngles extends Palabra {

    private String traduccion;

    public PalabraIngles(int id, String texto,int activated, String patron)
    {
        super(id, texto, activated, patron);

    }

    public void setTraduccion(String trad)
    {
        this.traduccion=trad;
    }

    public String getTraduccion() {
        return traduccion;
    }
}

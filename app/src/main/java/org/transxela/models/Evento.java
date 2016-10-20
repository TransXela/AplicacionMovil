package org.transxela.models;

import java.io.Serializable;

/**
 * Created by Vader on 14/09/2016.
 */
public class Evento implements Serializable {
    /*Principal*/
    private String Titulo;
    private String Ubicacion;
    private String Descripcion;
    private String Hora;
    /*Adjunto*/
    private String Path_imagen;

    /*Constructors*/
    public Evento() {
    }

    public Evento(String titulo, String ubicacion, String descripcion, String hora, String path_imagen) {
        Titulo = titulo;
        Ubicacion = ubicacion;
        Descripcion = descripcion;
        Hora = hora;
        Path_imagen = path_imagen;
    }

    /*GET*/
    public void Get_Evento(){

    }

    /*Getters*/

    public String getTitulo() {
        return Titulo;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getHora() {
        return Hora;
    }

    public String getPath_imagen() {
        return Path_imagen;
    }
}

package org.transxela.models;

import java.io.Serializable;

/**
 * Created by Vader on 14/09/2016.
 */
public class Denuncia implements Serializable {
    /*Principal*/
    private String Numero_Placa;
    private String Tipo_Denuncia;
    private String Descripcion;
    /*Ubicacion*/
    private String Latitud;
    private String Longiud;
    /*Archivo_adjunto*/
    private String Path_Imagen;

    public Denuncia() {
    }

    public Denuncia(String tipo_Denuncia, String numero_Placa, String descripcion, String longiud, String path_Imagen, String latitud) {
        Tipo_Denuncia = tipo_Denuncia;
        Numero_Placa = numero_Placa;
        Descripcion = descripcion;
        Longiud = longiud;
        Path_Imagen = path_Imagen;
        Latitud = latitud;
    }

    /*POST*/
    public void Insert (String IMEI){

    }

    /*GET*/
    public void Get_Denuncia (){

    }

}

package org.transxela.models;

import com.google.gson.GsonBuilder;

import org.transxela.models.deserializer.ConsejoDeserializer;
import org.transxela.models.deserializer.DenunciaDeserializer;

import java.io.Serializable;

/**
 * Created by Vader on 14/09/2016.
 */
public class Denuncia implements Serializable {
    /*Principal*/
    private String placa;
    private int tipodenuncia;
    private String descripcion;
    /*Ubicacion*/
    private float latitud;
    private float longitud;
    /*Archivo_adjunto*/
   // private String Path_Imagen;

    /*hora y fecha servidor*/
    private String fechahora;
    private Integer estado = null;


    public Denuncia() {
    }

    public Denuncia(String placa, int tipodenuncia, String descripcion, float latitud, float longitud, String fechahora, int estado) {
        this.placa = placa;
        this.tipodenuncia = tipodenuncia;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud= longitud;
        this.fechahora = fechahora;
        this.estado = estado;
    }

    public Denuncia(String placa, int tipodenuncia, String descripcion, float latitud, float longitud) {
        this.placa = placa;
        this.tipodenuncia = tipodenuncia;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getPlaca() {
        return placa;
    }

    public int getTipodenuncia() {
        return tipodenuncia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public String getFechahora() {
        return fechahora;
    }

    public int getEstado() {
        return estado;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setTipodenuncia(int tipodenuncia) {
        this.tipodenuncia = tipodenuncia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public static Denuncia getDenunciaFromJSON(String JSON){
        return new GsonBuilder().registerTypeAdapter(Denuncia.class, new DenunciaDeserializer())
                .create().fromJson(JSON, Denuncia.class);
    }
}

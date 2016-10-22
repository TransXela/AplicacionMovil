package org.transxela.models;

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
    private float longiud;
    /*Archivo_adjunto*/
   // private String Path_Imagen;

    /*hora y fecha servidor*/
    private String fechahora;
    private int estado;


    public Denuncia() {
    }

    public Denuncia(String placa, int tipodenuncia, String descripcion, float latitud, float longiud, String fechahora, int estado) {
        this.placa = placa;
        this.tipodenuncia = tipodenuncia;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longiud = longiud;
        this.fechahora = fechahora;
        this.estado = estado;
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

    public float getLongiud() {
        return longiud;
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

    public void setLongiud(float longiud) {
        this.longiud = longiud;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

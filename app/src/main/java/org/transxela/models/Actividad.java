package org.transxela.models;

import com.google.gson.GsonBuilder;

import org.transxela.models.deserializer.ActividadDeserializer;
import org.transxela.models.deserializer.DenunciaDeserializer;

import java.io.Serializable;

/**
 * Created by user on 13/11/2016.
 */

public class Actividad implements Serializable {
    private String nombre,descripcion,fecha,lugar,direccion;
    private float latitud,longitud;

    public Actividad() {
    }

    public Actividad(String nombre, String descripcion, String fecha, String lugar, String direccion, float latitud, float longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public static Actividad getActividadFromJSON(String JSON){
        return new GsonBuilder().registerTypeAdapter(Actividad.class, new ActividadDeserializer())
                .create().fromJson(JSON, Actividad.class);
    }
}

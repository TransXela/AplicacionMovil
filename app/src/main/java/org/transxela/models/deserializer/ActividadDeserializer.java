package org.transxela.models.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.transxela.models.Actividad;


import java.lang.reflect.Type;

/**
 * Created by user on 13/11/2016.
 */

public class ActividadDeserializer implements JsonDeserializer<Actividad> {
    @Override
    public Actividad deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject baseObject = json.getAsJsonObject();
        String nombre = baseObject.get("nombre").getAsString();
        String descripcion = baseObject.get("descripcion").getAsString();
        String fecha = "";
        if(baseObject.get("fecha").isJsonNull()){
            fecha = "Falta la maldita fecha!!!";
        } else {
            fecha = baseObject.get("fecha").getAsString();
        }
        String lugar = baseObject.get("lugar").getAsString();
        String direccion = baseObject.get("direccion").getAsString();
        float latitud = 0.0f;
        if(baseObject.get("latitud").isJsonNull()){
            latitud = 34.084f;
        } else {
            latitud = baseObject.get("latitud").getAsFloat();
        }
        float longitud = 0.0f;
        if(baseObject.get("longitud").isJsonNull()){
            longitud = -119.84f;
        } else {
            longitud = baseObject.get("longitud").getAsFloat();
        }
        return new Actividad(nombre,descripcion,fecha,lugar,direccion,latitud,longitud);
    }
}

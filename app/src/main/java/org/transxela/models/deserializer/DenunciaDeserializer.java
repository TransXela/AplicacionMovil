package org.transxela.models.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.transxela.models.Denuncia;

import java.lang.reflect.Type;

/**
 * Created by pblinux on 22/10/16.
 */

public class DenunciaDeserializer implements JsonDeserializer<Denuncia> {
    @Override
    public Denuncia deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject baseObject = json.getAsJsonObject();
        String placa = baseObject.get("placa").getAsString();
        int tipodenuncia = baseObject.get("tipodenuncia").getAsInt();
        String descripcion = baseObject.get("descripcion").getAsString();
        float latitud = baseObject.get("latitud").getAsFloat();
        float longitud = baseObject.get("longitud").getAsFloat();
        String fecha = baseObject.get("fechahora").getAsString().split("T")[0];
        int estado = baseObject.get("estado").getAsInt();
        return new Denuncia(placa, tipodenuncia, descripcion, latitud, longitud, fecha, estado);
    }
}

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
        JsonObject denunciaObject = baseObject.getAsJsonObject("denuncia");

        return null;
    }
}

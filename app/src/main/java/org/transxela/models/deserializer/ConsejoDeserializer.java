package org.transxela.models.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.transxela.models.Consejo;

import java.lang.reflect.Type;

/**
 * Created by user on 13/11/2016.
 */

public class ConsejoDeserializer implements JsonDeserializer<Consejo> {

    @Override
    public Consejo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject baseObject = json.getAsJsonObject();
        String consejo = baseObject.get("consejo").getAsString();

        return new Consejo(consejo);
    }
}
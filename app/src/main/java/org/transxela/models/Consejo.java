package org.transxela.models;

import com.google.gson.GsonBuilder;

import org.transxela.models.deserializer.ConsejoDeserializer;

import java.io.Serializable;

/**
 * Created by user on 13/11/2016.
 */

public class Consejo implements Serializable {
    private String consejo;

    public Consejo() {
    }

    public Consejo(String consejo) {

        this.consejo = consejo;
    }

    public String getConsejo() {
        return consejo;
    }

    public void setConsejo(String consejo) {
        this.consejo = consejo;
    }
    public static Consejo getConsejoFromJSON(String JSON){
        return new GsonBuilder().registerTypeAdapter(Consejo.class, new ConsejoDeserializer())
                .create().fromJson(JSON, Consejo.class);
    }
}

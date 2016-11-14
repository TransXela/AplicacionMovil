package org.transxela.models.wrappers;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.transxela.models.Denuncia;

/**
 * Created by Vader on 22/10/2016.
 */

public class DenunciaWrapper {
    @SerializedName("denuncia")
    private Denuncia denuncia;
    private String imei;

    public DenunciaWrapper(Denuncia denuncia,String imei) {
        this.denuncia = denuncia;
        this.imei = imei;
    }

    public String ToJson(){
        return new GsonBuilder().create().toJson(this);
    }
}

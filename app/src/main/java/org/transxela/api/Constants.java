package org.transxela.api;

import android.content.res.Resources;

import org.transxela.R;

/**
 * Created by pblinux on 21/10/16.
 */

public class Constants {
    public static int INPROCESS = 1;
    public static int DENIED = 2;
    public static int REPORTED = 3;

    public static int COBROILEGAL = 1;
    public static int MALESTADO = 2;
    public static int SOBRECARGO = 3;
    public static int ABUSO = 4;
    public static int NEGLIGENCIA= 5;

    public static String getTipoDenuncia(int i){
        String tipo = "";
        if(i == COBROILEGAL)
            tipo="Cobro Ilegal";
        if(i == MALESTADO)
            tipo="Unidad en mal estado";
        if(i == SOBRECARGO)
            tipo="Unidad Sobrecargada";
        if(i == ABUSO)
            tipo="Abuso Verbal";
        if(i == NEGLIGENCIA)
            tipo="Negligencia";
        return tipo;
    }

    public static String getEstadoDenuncia(int i){
        String estado = "";
        if(i == INPROCESS)
            estado = "En proceso";
        if(i == DENIED)
            estado = "Pendiente de verificacion";
        if(i == REPORTED)
            estado = "Reportada";
        return estado;
    }

}

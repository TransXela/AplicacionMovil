package org.transxela.api;

/**
 * Created by pblinux on 21/10/16.
 */

public class Endpoints {
    public static String BASE_URL = "https://api.transxela.site";
    public static String DEV_BASE_URL = "http://localhost:8000";
    public static String POSTDENUNCIA = BASE_URL + "/denuncia/";
    public static String GETDENUNCIA = BASE_URL + "/denuncia/?id={hash}";
    public static String GETALLDENUNCIAS = BASE_URL + "/denuncia/";
    public static String GETDASHBOARD = BASE_URL + "/cultura/consejoAct/";
}

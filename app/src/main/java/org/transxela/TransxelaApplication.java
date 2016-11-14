package org.transxela;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by pblinux on 21/10/16.
 */

public class TransxelaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());

    }
}

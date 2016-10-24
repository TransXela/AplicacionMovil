package org.transxela.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;
import org.transxela.R;
import org.transxela.api.Endpoints;
import org.transxela.models.Denuncia;
import org.transxela.models.wrappers.DenunciaWrapper;

import java.util.Arrays;
import java.util.List;

import info.hoang8f.widget.FButton;


/**
 * Created by pblinux on 14/09/16.
 */
public class DenunciaActivity extends AppCompatActivity implements Button.OnClickListener,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static List<String> SPINNERLIST = Arrays.asList("C", "P", "A");
    private static List<String> DENUNCIASLIST = Arrays.asList("Cobro Ilegal", "Unidad en mal estado", "Malos tratos", "Conduccion Temeraria", "Unidad en sobrecargada");
    private final static int LOCATION = 1;

    private Toolbar toolbar;

    private MaterialSpinner placaType;
    private FButton getLocationButton, setLocationButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private Denuncia denuncia;

    private AppCompatEditText placaNumber;
    private EditText denunciaDescription;
    private MaterialSpinner denunciaType;
    private float longitud =0.0f, latitud=0.0f ;

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
   // private float longitud,latitud;

    private GoogleApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        toolbar = (Toolbar) findViewById(R.id.denunciaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nueva Denuncia");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferencesEditor = preferences.edit();
        placaType = (MaterialSpinner) findViewById(R.id.placaType);
        placaType.setItems(SPINNERLIST);
        placaType.setSelectedIndex(0);

        getLocationButton = (FButton) findViewById(R.id.getLocationButton);
        getLocationButton.setButtonColor(getResources().getColor(R.color.colorPrimary));
        getLocationButton.setShadowColor(getResources().getColor(R.color.baseBackgroud));
        getLocationButton.setOnClickListener(this);

        setLocationButton = (FButton) findViewById(R.id.setLocationButton);
        setLocationButton.setButtonColor(getResources().getColor(R.color.colorPrimary));
        setLocationButton.setShadowColor(getResources().getColor(R.color.baseBackgroud));
        setLocationButton.setOnClickListener(this);

        placaNumber = (AppCompatEditText) findViewById(R.id.placaNumber);
        denunciaDescription = (EditText) findViewById(R.id.denunciaDescription);
        denunciaType = (MaterialSpinner) findViewById(R.id.denunciaType);
        denunciaType.setItems(DENUNCIASLIST);
        denunciaType.setSelectedIndex(0);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_denuncia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newDenuncia:
                try {
                    createDenuncia();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getLocationButton:
                obtenerUbicacion();
                Toast.makeText(getApplicationContext(),
                        "Ubicacion obtenida", Toast.LENGTH_SHORT).show();
                return;
            case R.id.setLocationButton:
                startActivityForResult(new Intent(getApplicationContext(), SetLocationActivity.class), LOCATION);
                Toast.makeText(getApplicationContext(),
                        "Ubicacion obtenida", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), SetLocationActivity.class));
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            longitud=(float)data.getDoubleExtra("Longitud",0.0);
            latitud=(float)data.getDoubleExtra("Latitud",0.0);
            Log.d("sirve",""+longitud);
        }

    }

    private void createDenuncia() throws JSONException {
        if(latitud==0.0f||longitud==0.0f){
            Toast.makeText(getApplicationContext(),
                    "Porfavor obtenga o seleccione su ubicacion", Toast.LENGTH_SHORT).show();
        }else{
        if (placaNumber.getText().toString().equals("") || denunciaDescription.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Campos ingresados incorrectamente", Toast.LENGTH_SHORT).show();
        }
        else {
            String placa = SPINNERLIST.get(placaType.getSelectedIndex()) + placaNumber.getText();
            int tipo = (denunciaType.getSelectedIndex())+1;
            String descripcion = denunciaDescription.getText().toString();
            Denuncia denuncia = new Denuncia(placa, tipo, descripcion, latitud, longitud);
            makeRequest(denuncia);
        }
        }
    }

    private void makeRequest(final Denuncia denuncia) throws JSONException {
        String body = new DenunciaWrapper(denuncia, preferences.getString("imei", "000000")).ToJson();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Denunciando");
        dialog.setMessage("Espere un momento");
        dialog.show();
        AndroidNetworking.post(Endpoints.POSTDENUNCIA)
                .addHeaders("Content-Type", "application/json")
                .addJSONObjectBody(new JSONObject(body))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            JSONObject denunciaObject = response.getJSONObject("denuncia");
                            if(denunciaObject.has("token")){
                                String token = denunciaObject.get("token").toString();
                                preferencesEditor.putString("token", token);
                                preferencesEditor.apply();
                                finish();
                                Toast.makeText(getApplicationContext(), "Denuncia realizada con exito", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response", response.toString());
                        if(dialog.isShowing())
                            dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("ERROR", error.getErrorBody().toString());
                        if(dialog.isShowing())
                            dialog.dismiss();
                        if(error.getErrorBody().contains("no tiene permitido hacer mas denuncias")){
                            finish();
                            Toast.makeText(getApplicationContext(), "No puede hacaer mas denuncias, intente mas tarde", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error, intente nuevamente", Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
            //pasar a otro metodo

    }
    private void updateUI(Location loc) {
        if (loc != null) {
            latitud=(float)loc.getLatitude();
            longitud=(float)loc.getLongitude();
            Log.d("yiah","si sirve");

        } else {
            Log.d("ña","no sirver");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }

    private void obtenerUbicacion (){

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }




}

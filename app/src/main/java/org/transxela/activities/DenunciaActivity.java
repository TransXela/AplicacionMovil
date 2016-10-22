package org.transxela.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
public class DenunciaActivity extends AppCompatActivity implements Button.OnClickListener {

    private static List<String> SPINNERLIST = Arrays.asList("C", "P", "A");
    private static List<String> DENUNCIASLIST = Arrays.asList("Cobro Ilegal", "Unidad en mal estado", "Malos tratos", "Conduccion Temeraria", "Unidad en sobrecargada");
    private final static int LOCATION = 1;

    private Toolbar toolbar;

    private MaterialSpinner placaType;
    private FButton getLocationButton, setLocationButton;
    private SharedPreferences preferences;
    private Denuncia denuncia;

    private AppCompatEditText placaNumber;
    private EditText denunciaDescription;
    private MaterialSpinner denunciaType;
    private float longitud = -118.453987f, latitud = 81.0003425f;


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
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        placaNumber = (AppCompatEditText) findViewById(R.id.placaNumber);
        denunciaDescription = (EditText) findViewById(R.id.denunciaDescription);
        denunciaType = (MaterialSpinner) findViewById(R.id.denunciaType);
        denunciaType.setItems(DENUNCIASLIST);
        denunciaType.setSelectedIndex(0);
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
                return;
            case R.id.setLocationButton:
                //startActivityForResult(new Intent(getApplicationContext(), SetLocationActivity.class), LOCATION);
                startActivity(new Intent(getApplicationContext(), SetLocationActivity.class));
                return;
        }
    }

    private void createDenuncia() throws JSONException {
        if (placaNumber.getText().toString().equals("") || denunciaDescription.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Campos ingresados incorrectamente", Toast.LENGTH_SHORT).show();
        } else {
            String placa = SPINNERLIST.get(placaType.getSelectedIndex()) + placaNumber.getText();
            int tipo = denunciaType.getSelectedIndex();
            String descripcion = denunciaDescription.getText().toString();
            Denuncia denuncia = new Denuncia(placa, tipo, descripcion, latitud, longitud);
            makeRequest(denuncia);
        }

    }

    private void makeRequest(Denuncia denuncia) throws JSONException {
        String body = new DenunciaWrapper(denuncia, preferences.getString("imei", "000000")).ToJson();
        AndroidNetworking.post(Endpoints.POSTDENUNCIA)
                .addHeaders("Content-Type", "application/json")
                .addJSONObjectBody(new JSONObject(body))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("respuesta", response.toString());
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}

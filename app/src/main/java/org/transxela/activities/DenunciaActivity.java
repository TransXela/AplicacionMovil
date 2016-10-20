package org.transxela.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.transxela.R;

import info.hoang8f.widget.FButton;


/**
 * Created by pblinux on 14/09/16.
 */
public class DenunciaActivity extends AppCompatActivity implements Button.OnClickListener {

    private static String[] SPINNERLIST = {"C", "P", "A"};
    private final static int LOCATION = 1;

    private Toolbar toolbar;

    private MaterialSpinner placaType;
    private FButton getLocationButton, setLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        toolbar = (Toolbar) findViewById(R.id.denunciaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nueva Denuncia");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        placaType = (MaterialSpinner) findViewById(R.id.placaType);
        placaType.setItems("C", "P", "A");
        placaType.setSelectedIndex(0);

        getLocationButton = (FButton) findViewById(R.id.getLocationButton);
        getLocationButton.setButtonColor(getResources().getColor(R.color.colorPrimary));
        getLocationButton.setShadowColor(getResources().getColor(R.color.baseBackgroud));
        getLocationButton.setOnClickListener(this);

        setLocationButton = (FButton) findViewById(R.id.setLocationButton);
        setLocationButton.setButtonColor(getResources().getColor(R.color.colorPrimary));
        setLocationButton.setShadowColor(getResources().getColor(R.color.baseBackgroud));
        setLocationButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_denuncia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getLocationButton:
                return;
            case R.id.setLocationButton:
                //startActivityForResult(new Intent(getApplicationContext(), SetLocationActivity.class), LOCATION);
                startActivity(new Intent(getApplicationContext(), SetLocationActivity.class));
                return;
        }
    }

    /*Drawable icon = getResources().getDrawable(R.mipmap.ic_directions_bus_white_24dp);
                icon = DrawableCompat.wrap(icon);
                if(hasFocus){
                    DrawableCompat.setTint(icon, Color.RED);
                    Toast.makeText(getApplicationContext(), "Focus", Toast.LENGTH_SHORT).show();
                } else {
                    DrawableCompat.setTint(icon, Color.BLUE);
                    Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                }
                ((AppCompatEditText)v).setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);*/
}

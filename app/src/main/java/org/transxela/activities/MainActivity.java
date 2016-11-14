package org.transxela.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.transxela.R;
import org.transxela.adapters.TabViewAdapter;
import org.transxela.fragments.FragmentDenuncia;
import org.transxela.fragments.FragmentHome;
import org.transxela.fragments.FragmentTrafico;

/**
 * Created by pblinux on 12/09/16.
 */
public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private static final int PHONE_STATE_PERMISSION_CODE = 15;

    private String imei;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferenceseditor;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager tabViewPager;
    private FloatingActionButton buttonNewDenuncia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Visual elements asignation
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        tabViewPager = (ViewPager) findViewById(R.id.mainContent);
        setupViewPager(tabViewPager);
        tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(tabViewPager);
        setupTabIcons();
        buttonNewDenuncia = (FloatingActionButton) findViewById(R.id.buttonNewDenuncia);
        buttonNewDenuncia.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferenceseditor = preferences.edit();
        saveImei();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, DenunciaActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PHONE_STATE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    imei = mngr.getDeviceId().toString();
                    saveImei();
                } else {
                    finish();
                    Toast.makeText(getApplicationContext(), "No puede utilizar la app sin este permiso", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabOne.setText("Cultura");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_location_on_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabTwo.setText("Mis Denuncias");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_report_problem_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabTwo);
        /*TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabThree.setText("Trafico");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_traffic_white_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);*/
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewAdapter tabAdapter = new TabViewAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentDenuncia(), "Mis Denuncias");
        tabAdapter.addFragment(new FragmentHome(), "Inicio");
        //tabAdapter.addFragment(new FragmentTrafico(), "Trafico");
        viewPager.setAdapter(tabAdapter);
    }


    private void obtenerImei() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_PERMISSION_CODE);
            }
        } else {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imei = mngr.getDeviceId().toString();
            saveImei();
        }
    }

    private void saveImei() {
        preferenceseditor.putString("imei", imei);
        preferenceseditor.apply();
    }
}

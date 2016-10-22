package org.transxela.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.transxela.R;
import org.transxela.adapters.TabViewAdapter;
import org.transxela.fragments.FragmentDenuncia;
import org.transxela.fragments.FragmentHome;
import org.transxela.fragments.FragmentTrafico;

/**
 * Created by pblinux on 12/09/16.
 */
public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

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
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferenceseditor=preferences.edit();
        saveImei();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, DenunciaActivity.class));
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabOne.setText("Inicio");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_home_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabTwo.setText("Mis Denuncias");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_report_problem_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_header, null);
        tabThree.setText("Trafico");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_traffic_white_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewAdapter tabAdapter = new TabViewAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentHome(), "Inicio");
        tabAdapter.addFragment(new FragmentDenuncia(), "Mis Denuncias");
        tabAdapter.addFragment(new FragmentTrafico(), "Trafico");
        viewPager.setAdapter(tabAdapter);
    }

    private  String obtenerImei(){
        TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return mngr.getDeviceId().toString();

    }

    private void saveImei(){
        preferenceseditor.putString("imei",obtenerImei());
        preferenceseditor.apply();
    }
}

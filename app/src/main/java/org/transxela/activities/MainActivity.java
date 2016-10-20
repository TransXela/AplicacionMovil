package org.transxela.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
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

}

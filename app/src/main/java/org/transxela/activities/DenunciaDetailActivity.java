package org.transxela.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.transxela.R;
import org.transxela.api.Constants;
import org.transxela.models.Denuncia;
import org.transxela.utils.FontManager;

/**
 * Created by pblinux on 17/10/16.
 */

public class DenunciaDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private Toolbar toolbar;

    protected MapView mMapView;
    protected Denuncia denuncia;

    protected TextView denunciaDate, denunciaType, denunciaStatus, denunciaStatusLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia_detail);
        denuncia = (Denuncia) getIntent().getSerializableExtra("denuncia_item");
        toolbar = (Toolbar) findViewById(R.id.denunciaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mi denuncia");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        denunciaDate = (TextView) findViewById(R.id.denunciaDate);
        denunciaType = (TextView) findViewById(R.id.denunciaType);
        denunciaStatus = (TextView) findViewById(R.id.denunciaStatus);
        denunciaStatusLabel = (TextView) findViewById(R.id.denunciaStatusLabel);
        mMapView = (MapView) findViewById(R.id.mapview);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(denunciaStatus, iconFont);

        denunciaDate.setText(denuncia.getFechahora());
        denunciaType.setText(Constants.getTipoDenuncia(denuncia.getTipodenuncia()));
        denunciaStatus.setText(getEstadoSymbolDenuncia(denuncia.getEstado()));
        denunciaStatus.setTextColor(getEstadoColorDenuncia(denuncia.getEstado()));
        denunciaStatusLabel.setText(Constants.getEstadoDenuncia(denuncia.getEstado()));
        denunciaStatusLabel.setTextColor(getEstadoColorDenuncia(denuncia.getEstado()));

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng coordinate = new LatLng(denuncia.getLatitud(), denuncia.getLongitud());
        MarkerOptions position = new MarkerOptions().position(coordinate);
        googleMap.addMarker(position);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17.0f));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                Log.e("TAG", "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    public String getEstadoSymbolDenuncia(int i){
        String symbol = "";
        if(i == 1)
            symbol = getResources().getString(R.string.fa_icon_inprocess);
        if(i == 3)
            symbol = getResources().getString(R.string.fa_icon_reported);
        if(i == 2)
            symbol = getResources().getString(R.string.fa_icon_denied);
        return symbol;
    }

    public int getEstadoColorDenuncia(int i){
        int symbol = 0;
        if(i == 1)
            symbol = getResources().getColor(R.color.denunciaInProccess);
        if(i == 3)
            symbol = getResources().getColor(R.color.denunciaReported);
        if(i == 2)
            symbol = getResources().getColor(R.color.denunciaDenied);
        return symbol;
    }
}

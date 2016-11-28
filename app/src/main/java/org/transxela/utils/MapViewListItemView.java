package org.transxela.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.transxela.R;

/**
 * Created by pblinux on 13/11/16.
 */

public class MapViewListItemView extends LinearLayout {

    protected MapView mMapView;
    protected TextView actividadTitle;
    protected TextView fecha;
    protected TextView hora;
    protected TextView ubicacion;

    public MapViewListItemView(Context context) {
        this(context, null);
    }

    public MapViewListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    private void setupView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.actividad_item, this);
        mMapView = (MapView) view.findViewById(R.id.mapview);
        actividadTitle = (TextView) view.findViewById(R.id.actividadTitle);
        fecha = (TextView) view.findViewById(R.id.fecha);
        hora = (TextView) view.findViewById(R.id.hora);
        ubicacion = (TextView) view.findViewById(R.id.ubicacion);
        setOrientation(VERTICAL);
    }

    public void mapViewOnCreate(Bundle savedInstanceState) {
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
        }
    }

    public void mapViewOnResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    public void mapViewOnPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    public void mapViewOnDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    public void mapViewOnLowMemory() {
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    public void mapViewOnSaveInstanceState(Bundle outState) {
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    public void setTitle(String text){
        actividadTitle.setText(text);
    }
    public void setFecha(String text){
        fecha.setText(text);
    }
    public void setHora(String text){
        hora.setText(text);
    }
    public void setUbicacion(String text){
        ubicacion.setText(text);
    }

    public void setLocation(final float latitud, final float longitud){
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng coordinate = new LatLng(latitud, longitud);
                MarkerOptions position = new MarkerOptions().position(coordinate);
                googleMap.addMarker(position);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17.0f));
                googleMap.getUiSettings().setAllGesturesEnabled(false);
            }
        });
    }
}

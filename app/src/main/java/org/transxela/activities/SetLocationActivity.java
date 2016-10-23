package org.transxela.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.transxela.R;

/**
 * Created by pblinux on 19/10/16.
 */

public class SetLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlocation);
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        float zoom=12.0f;

        //LatLngBounds bounds = new LatLngBounds(new LatLng(-15.0, 165.0), new LatLng(15.0, -165.0));
        //LatLngBounds XELA = new LatLngBounds(new LatLng(14.86, -91.56), new LatLng(14.88, -91.46));

        CameraPosition XELA_CAMERA = new CameraPosition.Builder().target(new LatLng(14.844875, -91.523197)).zoom(zoom).bearing(0).tilt(0).build();
        googleMap.setMinZoomPreference(zoom);
        googleMap.setMaxZoomPreference(16.0f);
       // googleMap.setLatLngBoundsForCameraTarget(XELA);
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(XELA_CAMERA));
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            float zoomito= cameraPosition.zoom;
                LatLngBounds XELA = new LatLngBounds(new LatLng(14.76, -91.56), new LatLng(14.96, -91.46));
                googleMap.setLatLngBoundsForCameraTarget(XELA);
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng));

            }
        });
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
}

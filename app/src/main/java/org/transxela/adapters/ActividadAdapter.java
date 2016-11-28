package org.transxela.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.transxela.R;
import org.transxela.models.Actividad;
import org.transxela.utils.MapViewListItemView;

import java.util.ArrayList;

/**
 * Created by pblinux on 13/11/16.
 */

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private Context context;
    private ArrayList<Actividad> actividades;

    public ActividadAdapter(Context context, ArrayList<Actividad> actividades) {
        this.context = context;
        this.actividades = actividades;
    }

    @Override
    public ActividadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MapViewListItemView mapViewListItemView = new MapViewListItemView(context);
        mapViewListItemView.mapViewOnCreate(null);
        return new ActividadViewHolder(mapViewListItemView);
    }

    @Override
    public void onBindViewHolder(ActividadViewHolder holder, int position) {
        Actividad actividad = actividades.get(position);
        String fecha,hora,fechahora;
        fechahora=actividad.getFecha();
        String[] output =fechahora.split("T");
        fecha=output[0];
        hora=(output[1]);
        String[] output2 =hora.split("Z");
        hora=output2[0];
        holder.mapViewListItemViewOnResume();
        holder.mMapViewListItemView.setTitle(actividad.getNombre());
        holder.mMapViewListItemView.setFecha(fecha);
        holder.mMapViewListItemView.setHora(hora);
        holder.mMapViewListItemView.setUbicacion(actividad.getDireccion());
        holder.mMapViewListItemView.setLocation(actividad.getLatitud(), actividad.getLongitud());
    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    protected class ActividadViewHolder extends RecyclerView.ViewHolder{

        protected MapViewListItemView mMapViewListItemView;

        public ActividadViewHolder(MapViewListItemView mapViewListItemView) {
            super(mapViewListItemView);
            mMapViewListItemView = mapViewListItemView;
        }

        public void mapViewListItemViewOnCreate(Bundle savedInstanceState) {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnCreate(savedInstanceState);
            }
        }

        public void mapViewListItemViewOnResume() {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnResume();
            }
        }

        public void mapViewListItemViewOnPause() {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnPause();
            }
        }

        public void mapViewListItemViewOnDestroy() {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnDestroy();
            }
        }

        public void mapViewListItemViewOnLowMemory() {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnLowMemory();
            }
        }

        public void mapViewListItemViewOnSaveInstanceState(Bundle outState) {
            if (mMapViewListItemView != null) {
                mMapViewListItemView.mapViewOnSaveInstanceState(outState);
            }
        }
    }
}

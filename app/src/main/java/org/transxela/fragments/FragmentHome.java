package org.transxela.fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.transxela.R;
import org.transxela.adapters.ActividadAdapter;
import org.transxela.adapters.DenunciaAdapter;
import org.transxela.api.Endpoints;
import org.transxela.models.Actividad;
import org.transxela.models.Consejo;
import org.transxela.utils.FontManager;

import java.util.ArrayList;

/**
 * Created by pblinux on 13/09/16.
 */
public class FragmentHome extends Fragment implements JSONObjectRequestListener, SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<Actividad> actividades;
    private Consejo consejo;
    private TextView consejoDia;
    private RecyclerView actividadList;
    private SwipeRefreshLayout actividadSwipe;
    private ActividadAdapter actividadAdapter;
    private SharedPreferences preferences;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actividades = new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        consejoDia = (TextView) view.findViewById(R.id.consejoContainer).findViewById(R.id.consejoDia);
        actividadList = (RecyclerView) view.findViewById(R.id.actividadList);
        actividadSwipe = (SwipeRefreshLayout) view.findViewById(R.id.actividadSwipe);
        actividadSwipe.setOnRefreshListener(this);
        actividadSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        if(preferences.getString("token", "") == ""){

        } else {
            actividadSwipe.post(new Runnable() {
                @Override
                public void run() {
                    actividadSwipe.setRefreshing(true);
                    loadDashboard();
                }
            });
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("reponse", response.toString());
        try {
            actividades.clear();
            JSONObject baseObject = response;
            JSONArray actividadObject = baseObject.getJSONArray("actividades");
            JSONArray consejoObject = baseObject.getJSONArray("consejo");
            consejo = Consejo.getConsejoFromJSON(consejoObject.get((consejoObject.length()-1)).toString());
            for(int i=0; i < actividadObject.length(); i++){
                Actividad newActividad = Actividad.getActividadFromJSON(actividadObject.get(i).toString());
                actividades.add(newActividad);
            }
            if(actividadSwipe.isRefreshing()){
                actividadSwipe.setRefreshing(false);
            }
            setContentFromModel();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(ANError anError) {
        if(actividadSwipe.isRefreshing()){
            actividadSwipe.setRefreshing(false);
        }
        Log.d("Error", anError.getErrorBody().toString());
    }


    @Override
    public void onRefresh() {
        loadDashboard();
    }

    private void loadDashboard(){
        AndroidNetworking.get(Endpoints.GETDASHBOARD)
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(this);
    }

    private void setContentFromModel(){
        Log.d("Consejo", consejo.getConsejo());
        consejoDia.setText(consejo.getConsejo());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        actividadList.setHasFixedSize(true);
        actividadList.setLayoutManager(mLayoutManager);
        actividadList.setItemAnimator(new DefaultItemAnimator());
        actividadAdapter = new ActividadAdapter(getActivity().getApplicationContext(), actividades);
        actividadList.setAdapter(actividadAdapter);
    }
}

package org.transxela.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.transxela.R;
import org.transxela.activities.DenunciaDetailActivity;
import org.transxela.adapters.DenunciaAdapter;
import org.transxela.api.Endpoints;
import org.transxela.models.Denuncia;

import java.util.ArrayList;

/**
 * Created by pblinux on 13/09/16.
 */
public class FragmentDenuncia extends Fragment implements JSONArrayRequestListener, SwipeRefreshLayout.OnRefreshListener, DenunciaAdapter.OnItemClickListener{

    private SharedPreferences preferences;

    private RecyclerView denunciaList;
    private SwipeRefreshLayout denunciaSwipe;
    private DenunciaAdapter denunciaAdapter;
    private ArrayList<Denuncia> denuncias;

    public FragmentDenuncia() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        denuncias = new ArrayList<Denuncia>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_denuncia, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        denunciaList = (RecyclerView) view.findViewById(R.id.denunciaList);
        denunciaSwipe = (SwipeRefreshLayout) view.findViewById(R.id.denunciaSwipe);
        denunciaSwipe.setOnRefreshListener(this);
        denunciaSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        if(preferences.getString("token", "") == ""){

        } else {
            getAllDenuncias();
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("response", response.toString());
        try{
            denuncias.clear();
            for (int i=response.length()-1; i>=0; i--){
                denuncias.add(Denuncia.getDenunciaFromJSON(response.get(i).toString()));
            }
            if(denunciaSwipe.isRefreshing()){
                denunciaSwipe.setRefreshing(false);
            }
            setContentFromModel();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(ANError anError) {
        Log.d("error", anError.getErrorBody());
    }

    @Override
    public void onRefresh() {
        getAllDenuncias();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Denuncia denuncia = denuncias.get(position);
        Intent intent = new Intent(getActivity().getApplicationContext(), DenunciaDetailActivity.class);
        intent.putExtra("denuncia_item", denuncia);
        startActivity(intent);
    }

    private void getAllDenuncias(){
        String token = preferences.getString("token", "");
        AndroidNetworking.get(Endpoints.GETALLDENUNCIAS)
                .addHeaders("Authorization", token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(this);
    }

    private void setContentFromModel(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        denunciaList.setHasFixedSize(true);
        denunciaList.setLayoutManager(mLayoutManager);
        denunciaList.setItemAnimator(new DefaultItemAnimator());
        denunciaAdapter = new DenunciaAdapter(getActivity().getApplicationContext(), denuncias);
        denunciaAdapter.setOnItemClickListener(this);
        denunciaList.setAdapter(denunciaAdapter);
    }
}

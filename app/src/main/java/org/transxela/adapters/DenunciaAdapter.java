package org.transxela.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.transxela.R;
import org.transxela.api.Constants;
import org.transxela.models.Denuncia;
import org.transxela.utils.FontManager;

import java.util.ArrayList;

/**
 * Created by pblinux on 22/10/16.
 */
public class DenunciaAdapter extends RecyclerView.Adapter<DenunciaAdapter.DenunciaViewHolder> {

    private Context context;
    private ArrayList<Denuncia> denuncias;

    public static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DenunciaAdapter(Context context, ArrayList<Denuncia> denuncias) {
        this.context = context;
        this.denuncias = denuncias;
    }

    @Override
    public DenunciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DenunciaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.denuncia_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DenunciaViewHolder holder, int position) {
        Denuncia denuncia = denuncias.get(position);
        String fecha = denuncia.getFechahora().split("T")[0];
        holder.denunciaDate.setText(fecha);
        holder.denunciaStatus.setText(getStatus(denuncia.getEstado()));
        holder.denunciaStatus.setTextColor(getStatusColor(denuncia.getEstado()));
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    private int getStatusColor(int numberStatus){
        int color = 0;
        if (numberStatus == Constants.INPROCESS)
            color = context.getResources().getColor(R.color.denunciaInProccess);
        if (numberStatus == Constants.DENIED)
            color = context.getResources().getColor(R.color.denunciaDenied);
        if (numberStatus == Constants.REPORTED)
            color = context.getResources().getColor(R.color.denunciaReported);
        return color;
    }

    private String getStatus(int numberStatus) {
        String status = "";
        if (numberStatus == Constants.INPROCESS)
            status = context.getString(R.string.fa_icon_inprocess);
        if (numberStatus == Constants.DENIED)
            status = context.getString(R.string.fa_icon_denied);
        if (numberStatus == Constants.REPORTED)
            status = context.getString(R.string.fa_icon_reported);
        return status;
    }

    protected class DenunciaViewHolder extends RecyclerView.ViewHolder {

        protected TextView denunciaStatus;
        protected TextView denunciaDate;

        public DenunciaViewHolder(final View itemView) {
            super(itemView);
            denunciaDate = (TextView) itemView.findViewById(R.id.denunciaDate);
            denunciaStatus = (TextView) itemView.findViewById(R.id.denunciaStatus);
            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(itemView.findViewById(R.id.denunciaStatus), iconFont);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }
}

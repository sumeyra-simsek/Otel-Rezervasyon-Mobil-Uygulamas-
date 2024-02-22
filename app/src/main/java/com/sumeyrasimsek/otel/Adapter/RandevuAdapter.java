package com.sumeyrasimsek.otel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumeyrasimsek.otel.Model.Randevu;
import com.sumeyrasimsek.otel.R;

import java.util.ArrayList;

public class RandevuAdapter extends RecyclerView.Adapter<RandevuAdapter.RCViewHolder> {

    public class RCViewHolder extends RecyclerView.ViewHolder{
        TextView textOdatipi,txtOdaNo,txtDateGidis,txtDateDonus;
        TextView txt_sehirismi,txt_otelismi;



        public RCViewHolder(@NonNull View itemView) {
            super(itemView);
            textOdatipi=itemView.findViewById(R.id.textOdatipi);
            txtDateDonus=itemView.findViewById(R.id.txtDateDonus);
            txtDateGidis=itemView.findViewById(R.id.txtDateGidis);
            txtOdaNo=itemView.findViewById(R.id.txtOdaNo);
            txt_sehirismi=itemView.findViewById(R.id.txt_sehirismi);
            txt_otelismi=itemView.findViewById(R.id.txt_otelismi);

        }
    }


    Context context;
    ArrayList<Randevu> modelArrayList;

    public RandevuAdapter(Context context, ArrayList<Randevu> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public RCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.date_item,parent,false);

        return new RCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCViewHolder holder, int position) {
        Randevu randevu= modelArrayList.get(position);
        holder.textOdatipi.setText(randevu.getOda());
        holder.txtDateGidis.setText(randevu.getGidis());
        holder.txtDateDonus.setText(randevu.getDonus());
        holder.txtOdaNo.setText(randevu.getId());
        holder.txt_sehirismi.setText(randevu.getName());
        holder.txt_otelismi.setText(randevu.getOtel());

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

}

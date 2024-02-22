package com.sumeyrasimsek.otel.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sumeyrasimsek.otel.Model.User;
import com.sumeyrasimsek.otel.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    FirebaseUser mevcutKullanici;
    String UserId;
    Context context;
    ArrayList<User> modelUserList;
    public UserAdapter(Context context, ArrayList<User> modelUserList) {
        this.context = context;
        this.modelUserList = modelUserList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.profilbilgisi,parent,false));*/
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mevcutKullanici= FirebaseAuth.getInstance().getCurrentUser();
        User user =modelUserList.get(position);
        holder.txtProfilname.setText(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= context.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
                        .edit();
                editor.putString("SehirId",user.getId());
                editor.apply();
            }
        });

    }



    @Override
    public int getItemCount() {
        return modelUserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProfilname;
        ImageView imgProfile;
        Button btnHesap,btnCikis;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProfilname=itemView.findViewById(R.id.txt_aramasehir);
            btnHesap=itemView.findViewById(R.id.txtProfilname);
            btnCikis=itemView.findViewById(R.id.btnCikis);

        }
    }
}

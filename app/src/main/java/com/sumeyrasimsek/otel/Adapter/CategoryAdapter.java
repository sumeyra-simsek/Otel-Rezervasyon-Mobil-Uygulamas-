package com.sumeyrasimsek.otel.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sumeyrasimsek.otel.Model.Category;
import com.sumeyrasimsek.otel.R;
import com.sumeyrasimsek.otel.cerceve.otelFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    FirebaseUser firebasecategory;
    Context context;
    List<Category> CategoryModels;

    public CategoryAdapter(Context context, List<Category> categoryModels) {
        this.context = context;
        CategoryModels = categoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.arama_ogesi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        firebasecategory = FirebaseAuth.getInstance().getCurrentUser();

        Category category = CategoryModels.get(position);
        holder.txt_aramaotel.setText(category.getOtel());
        holder.txt_aramasehir.setText(category.getName());
        Glide.with(context).load(CategoryModels.get(position).getImage())
                .into(holder.image_aramaogesi);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= context.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
                        .edit();
                editor.putString("SehirId",category.getId());
                editor.apply();

                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.cerceve_kapsayici,
                                new otelFragment()).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_aramasehir,txt_aramaotel;
        public CircleImageView image_aramaogesi;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_aramasehir=itemView.findViewById(R.id.txt_aramasehir);
            txt_aramaotel=itemView.findViewById(R.id.txt_aramaotel);
            image_aramaogesi=itemView.findViewById(R.id.image_aramaogesi);



        }
    }


}

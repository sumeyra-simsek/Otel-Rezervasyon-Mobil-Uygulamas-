package com.sumeyrasimsek.otel.cerceve;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sumeyrasimsek.otel.Adapter.RandevuAdapter;
import com.sumeyrasimsek.otel.Model.Randevu;
import com.sumeyrasimsek.otel.R;

import java.util.ArrayList;

public class DateFragment extends Fragment {
    FirebaseUser mevcutOtel;
    String otelId;
    RecyclerView recyclerView;
    ArrayList<Randevu> modelArrayList;
    RandevuAdapter randevuAdapter;
    TextView txtsil;
    Button btnsil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mevcutOtel= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        otelId=prefs.getString("SehirId","none");



        View view= inflater.inflate(R.layout.fragment_date, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        txtsil=view.findViewById(R.id.txtOdasecsil);
        btnsil=view.findViewById(R.id.btnsil);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        modelArrayList=new ArrayList<>();
        randevuAdapter=new RandevuAdapter(getContext(),modelArrayList);
        recyclerView.setAdapter(randevuAdapter);
        goster();
        btnsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sil();
            }
        });

        return view;
    }



    private void goster ()
    {

        DatabaseReference otelYolu = FirebaseDatabase.getInstance().getReference("randevular");
        otelYolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Randevu randevu=snapshot.getValue(Randevu.class);
                        modelArrayList.add(randevu);

                    }
                   randevuAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void sil()
    {
        String veriId= txtsil.getText().toString();
        DatabaseReference veriRef = FirebaseDatabase.getInstance().getReference().child("randevular").child(veriId);
        veriRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "randevu silindi", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), " hata", Toast.LENGTH_SHORT).show();

            }
        });

    }




    public DateFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
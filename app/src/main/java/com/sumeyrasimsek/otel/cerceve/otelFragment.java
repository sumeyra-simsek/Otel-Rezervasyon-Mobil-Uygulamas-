package com.sumeyrasimsek.otel.cerceve;

//import static com.sumeyrasimsek.otel.common.common.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sumeyrasimsek.otel.AnaSayfaActivity;
import com.sumeyrasimsek.otel.Model.Category;
import com.sumeyrasimsek.otel.R;

import java.util.ArrayList;
import java.util.List;


public class otelFragment extends Fragment {

    ViewPager2 viewPager;
    Context context;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public otelFragment(int contentLayoutId, Context context) {
        super(contentLayoutId);
        this.context = context;
    }

    FloatingActionButton btn_rezerve;
    TextView txt_otelismi;
    ImageSlider imageSlider;
    CheckedTextView txt_mail,txt_telefon;
    FirebaseUser mevcutOtel;
    ImageView Image,Image2,Image3;
    DatabaseReference db;
    String otelId;
    List<Category> CategoryModels;
    private String metin;
    public void setMetin(String metin) {
        this.metin = metin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_otel, container, false);




        mevcutOtel= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        otelId=prefs.getString("SehirId","none");

        imageSlider=view.findViewById(R.id.imageslider);
        txt_telefon=view.findViewById(R.id.txt_telefon);
        btn_rezerve=view.findViewById(R.id.btn_rezerve);
        txt_mail=view.findViewById(R.id.txt_mail);
        txt_otelismi=view.findViewById(R.id.txt_otelismi);
        otelbilgisi();


        btn_rezerve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnaSayfaActivity.fragmentManager.beginTransaction().replace(R.id.cerceve_kapsayici,new RezervasyonFragment(),null).commit();

            }
        });
        return view;
    }


    private void otelbilgisi(){
        DatabaseReference yolbilgisi= FirebaseDatabase.getInstance().getReference("category").child(otelId);

        yolbilgisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Category category=snapshot.getValue(Category.class);
                //if(category != null) {
                ArrayList<SlideModel> slideModels=new ArrayList<>();
                txt_mail.setText(category.getMail());
                txt_telefon.setText(category.getTelefon());
                txt_otelismi.setText(category.getOtel());
                slideModels.add(new SlideModel(category.getImage(),ScaleTypes.FIT));
                slideModels.add(new SlideModel(category.getImage2(),ScaleTypes.FIT));
                slideModels.add(new SlideModel(category.getImage3(),ScaleTypes.FIT));
                imageSlider.setImageList(slideModels);

              //  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
 public otelFragment(){

 }


}
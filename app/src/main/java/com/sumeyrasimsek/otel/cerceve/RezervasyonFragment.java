package com.sumeyrasimsek.otel.cerceve;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sumeyrasimsek.otel.Model.Category;
import com.sumeyrasimsek.otel.Model.Randevu;
import com.sumeyrasimsek.otel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class RezervasyonFragment extends Fragment {

    private Button btnDonus,btnGidis;
    private TextView txtGidis,txtDonus,txt_spinner,txt_odano,txtsehirname,txtotelname;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String otelId;
    FirebaseUser mevcutotel;
    private Spinner spinner,spinnerOda;
    private ArrayAdapter<CharSequence> adapterOdaTipi;
    private ArrayAdapter<Integer> AdapterOdaNo;
    private ArrayList<Integer> odalist;
    private DatabaseReference table_randevu;
    FloatingActionButton btn_rezerve;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rezervasyon, container, false);




        mevcutotel= FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        otelId=prefs.getString("SehirId","none");

        txtsehirname = view.findViewById(R.id.txtsehirname);
        txtotelname = view.findViewById(R.id.txtotelname);

        btn_rezerve = view.findViewById(R.id.btn_rezerve);
        btnDonus = view.findViewById(R.id.btnDonus);
        btnGidis = view.findViewById(R.id.btnGidis);
        txtDonus = view.findViewById(R.id.txtDonus);
        txtGidis = view.findViewById(R.id.txtGidis);
        spinner = view.findViewById(R.id.spinner);
        spinnerOda = view.findViewById(R.id.spinnerOda);
        txt_odano = view.findViewById(R.id.txt_odano);
        txt_spinner = view.findViewById(R.id.txt_spinner);
        table_randevu = FirebaseDatabase.getInstance().getReference("randevular");
        adapterOdaTipi = ArrayAdapter.createFromResource(getContext(), R.array.spinner, android.R.layout.simple_spinner_item);
        adapterOdaTipi.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterOdaTipi);
        kaydet();



        btn_rezerve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_spinner.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_spinner.setText(parent.getItemAtPosition(0).toString());

            }
        });
        odalist=new ArrayList<>();
        for(int i=1;i<=10;i++)
            odalist.add(i);
        AdapterOdaNo=new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_spinner_item,odalist);
        AdapterOdaNo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOda.setAdapter(AdapterOdaNo);
        spinnerOda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_odano.setText(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_odano.setText(parent.getItemAtPosition(0).toString());

            }
        });
        btnGidis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Seçilen tarihi formatlayarak TextView'de gösterelim

                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String selectedDate = dateFormat.format(calendar.getTime());
                                txtGidis.setText("Seçili Tarih: " + selectedDate);
                            }
                        }, year, month, day);

                // DatePickerDialog'da sadece gelecek tarihleri seçilebilir yapalım
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });
        btnDonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Seçilen tarihi formatlayarak TextView'de gösterelim
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String selectedDate = dateFormat.format(calendar.getTime());
                                txtDonus.setText("Seçili Tarih: " + selectedDate);
                            }
                        }, year, month, day);

                // DatePickerDialog'da sadece gelecek tarihleri seçilebilir yapalım
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();


            }

        });
        return view;


    }


    private void saveData() {
        String gidis = txtGidis.getText().toString().trim();
        String donus = txtDonus.getText().toString().trim();
        String odaTipi = txt_spinner.getText().toString().trim();
        String odaNo = txt_odano.getText().toString().trim();
        String otelname = txtotelname.getText().toString().trim();
        String sehirname = txtsehirname.getText().toString().trim();

        // Verileri Firebase veritabanına kaydet
        table_randevu.child(odaNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // ID numarası zaten varsa mesajı göster
                    Toast.makeText(getContext(), "Bu oda kullanılıyor.", Toast.LENGTH_SHORT).show();
                } else {
                    Randevu randevu = new Randevu(gidis,donus,odaTipi,odaNo,otelname,sehirname);
                    table_randevu.child(odaNo).setValue(randevu)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Randevu başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Randevu kaydedilirken hata oluştu.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void kaydet(){
        DatabaseReference yolbilgisi= FirebaseDatabase.getInstance().getReference("category").child(otelId);
        yolbilgisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Category category=snapshot.getValue(Category.class);
                txtotelname.setText(category.getOtel());
                txtsehirname.setText(category.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RezervasyonFragment() {
        // Required empty public constructor
    }


    public static RezervasyonFragment newInstance(String param1, String param2) {
        RezervasyonFragment fragment = new RezervasyonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
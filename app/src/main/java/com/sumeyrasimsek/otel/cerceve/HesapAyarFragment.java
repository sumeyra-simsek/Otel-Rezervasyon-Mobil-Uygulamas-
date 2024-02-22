package com.sumeyrasimsek.otel.cerceve;

import static android.content.Context.MODE_PRIVATE;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sumeyrasimsek.otel.Model.User;
import com.sumeyrasimsek.otel.R;

import java.util.HashMap;
import java.util.Map;


public class HesapAyarFragment extends Fragment {

    Button fotoyukle;
    FloatingActionButton Guncelle;
    EditText edtSifre,edtisim;
    ImageView imgyukle;
    private Uri imageUri;
    FirebaseUser mevcutKullanici;
    String id;
    final private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    final  private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_hesap_ayar, container, false);

               mevcutKullanici= FirebaseAuth.getInstance().getCurrentUser();
               SharedPreferences prefs=getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
               id=prefs.getString("UserId","none");
               DatabaseReference yol= FirebaseDatabase.getInstance().getReference("Users").child(id);
              fotoyukle=view.findViewById(R.id.btnfotoyukle);
              Guncelle=view.findViewById(R.id.FbtnGuncelle);
              edtisim=view.findViewById(R.id.edtIsim);
              edtSifre=view.findViewById(R.id.edtSifre);
              imgyukle=view.findViewById(R.id.imgyukle);

              Guncelle.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      guncelle();
                  }
              });

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imgyukle.setImageURI(imageUri);
                        }
                        else{
                            Toast.makeText(getActivity(), "Resim Seçilmedi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        imgyukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        fotoyukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);

                }
                else {
                    Toast.makeText(getActivity(), "Lütfen resim seçiniz.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void guncelle(){
        DatabaseReference kullaniciYolu = FirebaseDatabase.getInstance().getReference("Users").child(id);
        String yeniisim=edtisim.getText().toString().trim();
        String yenisifre=edtSifre.getText().toString().trim();
        kullaniciYolu.child("name").setValue(yeniisim,((error, ref) -> {
            if (error == null) {
                Toast.makeText(getActivity(), "İsim Değiştirildi", Toast.LENGTH_SHORT).show();
            } else {
                // İlk veri güncellenirken bir hata oluştuğunda yapılacak işlemler
            }
        }));
        kullaniciYolu.child("password").setValue(yenisifre,((error, ref) -> {
            if (error==null) {
                Toast.makeText(getActivity(), "Şifre Değiştirildi.", Toast.LENGTH_SHORT).show();
            }
        }));

    }


    private void uploadToFirebase(Uri uri){
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                       imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                         @Override
                                         public void onSuccess(Uri uri) {
                                             DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                                             databaseReference.child("imgUrl").setValue(uri.toString());
                                             Toast.makeText(getActivity(), "yüklendi", Toast.LENGTH_SHORT).show();

                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             Toast.makeText(getActivity(), "yükleme başarısız", Toast.LENGTH_SHORT).show();
                                         }
                                     })    ;
                           }

                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(getActivity(), "yükleme başarısız", Toast.LENGTH_SHORT).show();

                           }
                       });

    }

    private String getFileExtension(Uri fileUri)
    {
        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

    public HesapAyarFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
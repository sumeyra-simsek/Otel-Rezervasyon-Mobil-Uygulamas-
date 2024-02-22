package com.sumeyrasimsek.otel.cerceve;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sumeyrasimsek.otel.R;

public class MapsFragment extends Fragment {
    GoogleMap googleMap;




private final static int REQUEST_LOCATION =90;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng izmir = new LatLng(38.290828, 26.377714);
            googleMap.addMarker(new MarkerOptions().position(izmir).title("Golden Otel"));
            LatLng mugla = new LatLng(36.856403, 28.284936);
            googleMap.addMarker(new MarkerOptions().position(mugla).title("Çiçek Otel"));

            LatLng Antalya = new LatLng(36.225407, 29.625455);
            googleMap.addMarker(new MarkerOptions().position(Antalya).title("Menekşe Otel"));




            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                googleMap.setMyLocationEnabled(true);
            }
            else {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
                }

            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mugla,8));

        }


    };




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode ==REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(getActivity(), "Konum izni verilmedi", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}
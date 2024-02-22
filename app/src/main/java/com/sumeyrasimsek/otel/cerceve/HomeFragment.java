package com.sumeyrasimsek.otel.cerceve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.sumeyrasimsek.otel.R;


public class HomeFragment extends Fragment{
    VideoView vv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        vv=view.findViewById(R.id.videoView);
        vv.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.otelvideo);
        MediaController med= new MediaController(getContext());
        vv.setMediaController(med);
        med.setAnchorView(vv);
        vv.start();



        return view;



    }




    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



}
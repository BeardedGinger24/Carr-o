package com.example.carr_o.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carr_o.NewLogActivity;
import com.example.carr_o.R;

import java.util.zip.Inflater;

public class LogFragment extends Fragment{
    Context context;
    ConstraintLayout mLogFragment;
    BottomNavigationView mBottomNav;

    public static final int NEW_LOG_ACTIVITY_REQUEST_CODE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_log);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
//        View view1 = inflater.inflate(R.layout.activity_main, container, false);

//        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
//        int navHeight = navigation.getHeight();
//        Log.d("NAVheight", "nav: " + navHeight);

//        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

//        mLogFragment = (ConstraintLayout) view.findViewById(R.id.log_frag);
//        int logHeight = mLogFragment.getMaxHeight();
//        Log.d("test", "onCreateView: " + logHeight);
//        mLogFragment.setMaxHeight(logHeight - 200);

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewLogActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}

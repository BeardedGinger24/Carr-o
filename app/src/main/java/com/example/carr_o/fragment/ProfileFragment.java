package com.example.carr_o.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carr_o.HomeAdapter;
import com.example.carr_o.R;
import com.example.carr_o.model.VINDecode;
import com.example.carr_o.model.VINDecodeViewModel;

import java.text.DecimalFormat;
import java.util.List;


public class ProfileFragment extends Fragment {
    VINDecodeViewModel vinDecodeViewModel;

    EditText mVIN;
    EditText mMiles;
    Button mSubmit;

    CardView mCarCard;
    TextView mCarInfo;
    TextView mCarEngine;
    TextView mTankSize;
    TextView mMPG;
    TextView mCurrentMiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mCarCard = (CardView) view.findViewById(R.id.cv_car);

        mCarInfo = (TextView) view.findViewById(R.id.car_info_base);
        mCarEngine = (TextView) view.findViewById(R.id.car_engine);
        mTankSize = (TextView) view.findViewById(R.id.car_tank_size);
        mMPG = (TextView) view.findViewById(R.id.car_mpg);
        mCurrentMiles = (TextView) view.findViewById(R.id.car_current_miles);

        vinDecodeViewModel = ViewModelProviders.of(this).get(VINDecodeViewModel.class);
        vinDecodeViewModel.getAllLogs().observe(this, new Observer<List<VINDecode>>() {
            @Override
            public void onChanged(@Nullable List<VINDecode> vinDecodes) {
                if(!vinDecodes.isEmpty()) {
                    double miles = vinDecodes.get(0).getCurrent_miles();
                    DecimalFormat milesFormatted = new DecimalFormat("#,###");

                    mCarInfo.setText(vinDecodes.get(0).getYear() + " " + vinDecodes.get(0).getMake() + " " + vinDecodes.get(0).getModel());
                    mCarEngine.setText(vinDecodes.get(0).getEngine());
                    mTankSize.setText(vinDecodes.get(0).getTank_size());
                    mMPG.setText(vinDecodes.get(0).getHighway_miles() + " HWY/" + vinDecodes.get(0).getCity_miles() + " City");
                    mCurrentMiles.setText(milesFormatted.format(miles) + " miles");
                } else {
                    showSubmit();

                }
            }
        });

        mVIN = (EditText) view.findViewById(R.id.et_vin);
        mMiles = (EditText) view.findViewById(R.id.et_current_miles);
        mSubmit = (Button) view.findViewById(R.id.submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVINMiles(v);
            }
        });

        return view;
    }

    private void showSubmit() {
        mCarCard.setVisibility(View.GONE);
        mVIN.setVisibility(View.VISIBLE);
        mMiles.setVisibility(View.VISIBLE);
        mSubmit.setVisibility(View.VISIBLE);
    }

    void submitVINMiles(View view){
        String vin = mVIN.getText().toString();
        int miles = Integer.parseInt(mMiles.getText().toString());

        vinDecodeViewModel.update(vin, miles);

       hideSubmit();
    }

    void hideSubmit(){
        mVIN.setVisibility(View.GONE);
        mMiles.setVisibility(View.GONE);
        mSubmit.setVisibility(View.GONE);
        mCarCard.setVisibility(View.VISIBLE);
    }
}

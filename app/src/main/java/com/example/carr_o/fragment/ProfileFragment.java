package com.example.carr_o.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carr_o.HomeAdapter;
import com.example.carr_o.R;
import com.example.carr_o.model.VINDecode;
import com.example.carr_o.model.VINDecodeViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    VINDecodeViewModel vinDecodeViewModel;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    TextView mUserName;
    ImageView mProfilePicture;
    TextView mStatus;

    EditText mVIN;
    EditText mMiles;
    Button mSubmit;

    CardView mCarCard;
    TextView mCarInfo;
    TextView mCarEngine;
    TextView mTankSize;
    TextView mMPG;
    TextView mCurrentMiles;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        mCarCard = (CardView) view.findViewById(R.id.cv_car);

        // Account
        mUserName = (TextView) view.findViewById(R.id.tv_username);
        mProfilePicture = (ImageView) view.findViewById(R.id.iv_profile_picture);
        mStatus = (TextView) view.findViewById(R.id.tv_signin_text);

        // Button listeners
        view.findViewById(R.id.signInButtonMine).setOnClickListener(this);
        view.findViewById(R.id.signOutButtonMine).setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

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

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(view.findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
        if (user != null) {
            mUserName.setText(user.getDisplayName());
            mStatus.setText(user.getEmail());
            mStatus.setTextSize(18);
            Uri imageLink = user.getPhotoUrl();

            if(imageLink != null){
                Picasso.get().load(imageLink).into(mProfilePicture);
            }
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            view.findViewById(R.id.signInButtonMine).setVisibility(View.GONE);
            view.findViewById(R.id.signOutButtonMine).setVisibility(View.VISIBLE);
//            view.findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
            mStatus.setText(R.string.please_signin);
            mStatus.setTextSize(36);
            mProfilePicture.setImageResource(R.drawable.ic_person_white);
            mUserName.setText("");
            view.findViewById(R.id.signOutButtonMine).setVisibility(View.GONE);
            view.findViewById(R.id.signInButtonMine).setVisibility(View.VISIBLE);
//            view.findViewById(R.id.signOutAndDisconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInButtonMine) {
            signIn();
        } else if (i == R.id.signOutButtonMine) {
            signOut();
        } else if (i == R.id.disconnectButton) {
            revokeAccess();
        }
    }
}

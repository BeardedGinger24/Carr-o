package com.example.carr_o.fragment;

import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carr_o.HomeAdapter;
import com.example.carr_o.LogRecyclerViewAdapter;
import com.example.carr_o.R;
import com.example.carr_o.data.LogViewModel;
import com.example.carr_o.data.LogViewModelFactory;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.ListItemClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    int Current_Mileage;
    VINDecodeViewModel mVINViewModel;
    private LogViewModel mLogViewModel2;


//    private ActionBar toolbar;

    private LogViewModel mLogViewModel;
    RecyclerView recyclerView;
    HomeAdapter mHomeAdapter;

//    ArrayList<VINDecode> carInfo;
    TextView mUserName;
    ImageView mImageStatus;
    TextView mTextStatus;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUserName = (TextView) view.findViewById(R.id.users_name);
        mImageStatus = (ImageView) view.findViewById(R.id.status_image);
        mTextStatus = (TextView) view.findViewById(R.id.status_text);

        mVINViewModel = ViewModelProviders.of(this).get(VINDecodeViewModel.class);
        mVINViewModel.getAllLogs().observe(this, new Observer<List<VINDecode>>() {
            @Override
            public void onChanged(@Nullable List<VINDecode> vinDecodes) {
                Current_Mileage = vinDecodes.get(0).getCurrent_miles();
                final Date today = new Date();
                Log.d("Current miles", "onCreateView: " + Current_Mileage);

                mLogViewModel2 = ViewModelProviders.of(HomeFragment.this, new LogViewModelFactory(getActivity().getApplication(), 0)).get(LogViewModel.class);
                mLogViewModel2.getAllLogs(-1).observe(HomeFragment.this, new Observer<List<com.example.carr_o.data.Log>>() {
                    @Override
                    public void onChanged(@Nullable List<com.example.carr_o.data.Log> logs) {
                        if(logs.size() > 0){
                            for(int i = 0; i < logs.size(); i++){
                                String date = logs.get(i).getDate();
                                DateFormat srcDf = new SimpleDateFormat("MMM dd, yyyy");
                                // parse the date string into Date object

                                try {
                                    Date convertedDate = srcDf.parse(date);
//
//                                    Calendar cal1 = Calendar.getInstance();
//                                    Calendar cal2 = Calendar.getInstance();
//                                    cal1.setTime(today);
//                                    cal2.setTime(convertedDate);

//                                    boolean yearGap =

                                    if(logs.get(i).getMaintType().equals("Oil Change")) {
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 183) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 3000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if (logs.get(i).getMaintType().equals( "Engine Air Filter")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 365) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 10000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Spark Plug")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 1095) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 30000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Coolant")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 1825) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 60000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Transmission Fluid")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 1095) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 30000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Brakes")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 1460) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 50000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Brake Fluid")){
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 730) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 20000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    } else if(logs.get(i).getMaintType().equals("Headlight Fluid")){
                                        mTextStatus.setText("Why you yellin at the mic?");
                                    } else{
                                        long timeSpan = today.getTime() - convertedDate.getTime();
                                        if (timeSpan / ((1000 * 60 * 60 * 24)) > 183) {
                                            Log.d("Got time", "onChanged: ");
                                            mLogViewModel2.update(logs.get(i));
                                        } else if (Current_Mileage - logs.get(i).getMileage() > 3000) {
                                            mLogViewModel2.update(logs.get(i));
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }



                            }
                        }
                    }
                });
            }
        });



        recyclerView = view.findViewById(R.id.recycler_view);
        mLogViewModel = ViewModelProviders.of(this, new LogViewModelFactory(getActivity().getApplication(), 0)).get(LogViewModel.class);
        final HomeAdapter adapter = new HomeAdapter(getContext(), mLogViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mLogViewModel.getAllLogs(0).observe(this, new Observer<List<com.example.carr_o.data.Log>>() {
            @Override
            public void onChanged(@Nullable final List<com.example.carr_o.data.Log> logs) {
                adapter.setLogs(logs);
                if(adapter.getItemCount() == 0){
                    Log.d("Count", "onChanged: Has obseerver");
                    mImageStatus.setImageResource(R.drawable.checkmark_64);
                    mTextStatus.setText(R.string.good_terms);
                } else if(adapter.getItemCount() > 0) {
                    mImageStatus.setImageResource(R.drawable.ic_warning_black_24dp);
                    mTextStatus.setText(R.string.bad_terms);
                }
            }
        });

//        carInfo = new ArrayList<>();
//        mHomeAdapter = new HomeAdapter(carInfo);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mCarInfo.setLayoutManager(layoutManager);
//
//        mCarInfo.setHasFixedSize(true);


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

//        makeNewsSearchQuery();

//        fetchStoreItems();

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

//    private void makeNewsSearchQuery(){
//        URL newsSearchUrl = NetworkUtils.buildUrl();
//        new VINQueryTask().execute(newsSearchUrl);
//    }
//
//    public class VINQueryTask extends AsyncTask<URL, Void, String>{
//       @Override
//       protected void onPreExecute() {
//           super.onPreExecute();
//       }
//
//       @Override
//       protected String doInBackground(URL... urls) {
//           URL searchUrl = urls[0];
//           String newsSearchResults = null;
//           try {
//               newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//           return newsSearchResults;
//       }
//
//       @Override
//       protected void onPostExecute(String newsSearchResults) {
//           super.onPostExecute(newsSearchResults);
//           carInfo = JsonUtils.parseJson(newsSearchResults);
//
//           mHomeAdapter = new HomeAdapter( carInfo);
//           mCarInfo.setAdapter(mHomeAdapter);
//           //String test = articles.get(0).getAuthor();
//           //mNewsJsonResults.setText(test);
//       }
//   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
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
        if (user != null) {
            mUserName.setText(user.getDisplayName());
        } else {
            mUserName.setText(R.string.new_comer);
        }
    }
}

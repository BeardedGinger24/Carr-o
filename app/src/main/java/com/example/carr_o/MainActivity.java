package com.example.carr_o;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

//import com.example.carr_o.fragment.MapsFragment;
import com.example.carr_o.fragment.HomeFragment;
import com.example.carr_o.fragment.LogFragment;
import com.example.carr_o.fragment.ProfileFragment;
//import com.example.carr_o.helper.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentHome();

    }

    private void fragmentHome(){
        Fragment fragment;
        toolbar.setTitle("Home");
        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_maps:
                    toolbar.setTitle("Logs");
                    fragment = new LogFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home:
                    fragmentHome();
//                    toolbar.setTitle("Home");
//                    fragment = new HomeFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}

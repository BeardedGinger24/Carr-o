package com.example.carr_o.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.carr_o.LogRecyclerViewAdapter;
import com.example.carr_o.NewLogActivity;
import com.example.carr_o.R;
import com.example.carr_o.data.Log;
import com.example.carr_o.data.LogViewModel;
import com.example.carr_o.data.LogViewModelFactory;

import java.util.List;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class LogFragment extends Fragment{
    private LogViewModel mLogViewModel;
    RecyclerView recyclerView;
    int mileage;
    double price;

    SearchView mSearchList;
    Button mSearchButton;
    String search;
    private List<Log> mLogs;
    private LogRecyclerViewAdapter adaptetr;

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

        mSearchList = view.findViewById(R.id.searchView);
        mSearchButton = view.findViewById(R.id.search_button);



        recyclerView = view.findViewById(R.id.log_recycler_view);
        mLogViewModel = ViewModelProviders.of(this, new LogViewModelFactory(getActivity().getApplication(), search)).get(LogViewModel.class);
        final LogRecyclerViewAdapter adapter = new LogRecyclerViewAdapter(getContext(), mLogViewModel, mLogs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mLogViewModel.getAllLogs().observe(this, new Observer<List<Log>>() {
            @Override
            public void onChanged(@Nullable final List<Log> logs) {
                adapter.setLogs(logs);

            }
        });

        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewLogActivity.class);
                startActivityForResult(intent, NEW_LOG_ACTIVITY_REQUEST_CODE);
            }
        });

        mSearchList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    adapter.getFilter().filter(newText);

                return false;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void searchQuery(String query){
        search = query;
        android.util.Log.d("SEARCH", "onQueryTextSubmit: " + search);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_LOG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Log log = new Log(data.getStringExtra(NewLogActivity.EXTRA_REPLY_DATE),
                    data.getStringExtra(NewLogActivity.EXTRA_REPLY_LOCATION),
                    data.getIntExtra(NewLogActivity.EXTRA_REPLY_MILEAGE, mileage),
                    data.getDoubleExtra(NewLogActivity.EXTRA_REPLY_PRICE, price),
                    data.getStringExtra(NewLogActivity.EXTRA_REPLY_MAINTENANCE_TYPE),
                    data.getStringExtra(NewLogActivity.EXTRA_REPLY_NOTES));
            mLogViewModel.insert(log);
        }
    }
}

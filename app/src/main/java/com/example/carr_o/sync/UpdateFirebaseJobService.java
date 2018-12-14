package com.example.carr_o.sync;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import com.example.carr_o.MainActivity;
import com.example.carr_o.data.LogRepository;
import com.example.carr_o.fragment.HomeFragment;
import com.example.carr_o.model.VINDecodeRepository;
import com.example.carr_o.model.VINDecodeViewModel;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class UpdateFirebaseJobService extends JobService{
    private AsyncTask mBackgroundTask;
//    NewsItemRepository mNewsItemRepo;

//    VINDecodeViewModel vinDecodeViewModel;
    VINDecodeRepository mRepo;
    int CurrentMiles;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = UpdateFirebaseJobService.this;
                UpdateTask.executeTask(context, UpdateTask.UPDATE_NEWS);

//                VINDecodeViewModel vinDecodeViewModel = new VINDecodeViewModel(getApplication());
//
//
//                mRepo = new VINDecodeRepository(vinDecodeViewModel.getApplication());
//                CurrentMiles = mRepo.getMiles(getApplication());

//                Fragment fragment = new GetValues();
//
//                GetValues miles = new GetValues();
//                CurrentMiles = miles.getMiles();
//                Log.d("MILES", "doInBackground: " + CurrentMiles);
//                mNewsItemRepo = new NewsItemRepository(getApplication());
//                mNewsItemRepo.makeNewsSearchQuery();
                Log.d("ASYNCTASK", "doInBackground: HIIII");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false );
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }

    public static class GetValues extends Fragment{
        int CurrentMiles;
        VINDecodeViewModel vinDecodeViewModel;


//        @Override
//        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//            super.onViewCreated(view, savedInstanceState);
//            vinDecodeViewModel = ViewModelProviders.of(this).get(VINDecodeViewModel.class);
//            CurrentMiles = vinDecodeViewModel.getAllLogs().getValue().get(0).getCurrent_miles();
//            setMiles(CurrentMiles);
//        }

        public void setMiles(int miles){
            CurrentMiles = miles;
        }

        public int getMiles(){
            vinDecodeViewModel = ViewModelProviders.of(this).get(VINDecodeViewModel.class);
            CurrentMiles = vinDecodeViewModel.getAllLogs().getValue().get(0).getCurrent_miles();
            return CurrentMiles;
        }
    }
}

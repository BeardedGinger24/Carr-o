package com.example.carr_o.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class LogViewModel extends AndroidViewModel {
    private LogRepository mRepository;
    private LiveData<List<Log>> mAllLogs;

    public LogViewModel (Application application, String search, int status) {
        super(application);

        mRepository = new LogRepository(application, search);
        if(search == null || search.isEmpty()) {
            mAllLogs = mRepository.getAllLogs();
        } else if (status == 0){
            mAllLogs = mRepository.getAllLogs(status);
        } else {
            mAllLogs = mRepository.getSearch(search);
        }
    }

    public LiveData<List<Log>> getAllLogs() {
//        return getAllLogs("autozone");
        return mAllLogs;
    }

    public LiveData<List<Log>> getAllLogs(int status) {
        mAllLogs = mRepository.getAllLogs(status);
        return mAllLogs;
    }

    public LiveData<List<Log>> getAllLogs(String search) {
        android.util.Log.d("ViewModel Search", "getAllLogs: " + search);
        if(search.trim().isEmpty()){
            mAllLogs = mRepository.getAllLogs();
        } else{
//            search = search.trim();
            mAllLogs = mRepository.getAllLogs(search);
        }

        return mAllLogs;
    }

    public void update(Log log){
        mRepository.update(log);
    }

    public void insert(Log log) {
        mRepository.insert(log);
    }

    public void delete(Log log){
        mRepository.delete(log);
    }
}

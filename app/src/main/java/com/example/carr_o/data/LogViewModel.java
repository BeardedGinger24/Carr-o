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

    public LogViewModel (Application application, String search) {
        super(application);

        mRepository = new LogRepository(application, search);
        if(search == null || search.isEmpty()) {
            mAllLogs = mRepository.getAllLogs();
        } else {
            mAllLogs = mRepository.getSearch(search);
        }

    }

    public LiveData<List<Log>> getAllLogs() {
        return mAllLogs;
    }

    public void insert(Log log) {
        mRepository.insert(log);
    }

    public void delete(Log log){
        mRepository.delete(log);
    }
}

package com.example.carr_o.data;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class LogViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mSearch;

    public LogViewModelFactory(Application application, String search){
        mApplication = application;
        mSearch = search;
    }

    @Override
    public <T extends ViewModel> T create( Class<T> modelClass) {
        return (T) new LogViewModel(mApplication, mSearch);
    }
}

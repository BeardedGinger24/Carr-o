package com.example.carr_o.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.carr_o.data.Log;
import com.example.carr_o.data.LogRepository;

import java.util.List;

public class VINDecodeViewModel extends AndroidViewModel {
    private VINDecodeRepository mRepository;
    private LiveData<List<VINDecode>> mAllLogs;

    public VINDecodeViewModel (Application application) {
        super(application);
        mRepository = new VINDecodeRepository(application);
        mAllLogs = mRepository.getAllLogs();

    }

    public void update(String vin, int miles){mRepository.makeNewsSearchQuery(vin, miles);}

    public void updateMiles(int miles){
        mRepository.updateMiles(miles);
    }

    public LiveData<List<VINDecode>> getAllLogs() {
        return mAllLogs;
    }

    public void insert(VINDecode vinDecode) {
        mRepository.insert(vinDecode);
    }

    public void delete(VINDecode vinDecode){
        mRepository.delete(vinDecode);
    }
}

package com.example.carr_o.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.carr_o.HomeAdapter;
import com.example.carr_o.data.Log;
import com.example.carr_o.data.LogDao;
import com.example.carr_o.utilities.JsonUtils;
import com.example.carr_o.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VINDecodeRepository {

    private VINDecodeDao mVINDecodeDao;
    private LiveData<List<VINDecode>> mAllCars;

    private VINDecode car;
    int Miles = 0;

    public VINDecodeRepository(Application application){
        VINDecodeDatabase db = VINDecodeDatabase.getDatabase(application.getApplicationContext());
        mVINDecodeDao = db.vinDecodeDao();
        mAllCars = mVINDecodeDao.getAllCars();
    }

    LiveData<List<VINDecode>> getAllLogs() {
        return mAllCars;
    }

    public int getMiles(Application application){
//        VINDecodeDatabase db = VINDecodeDatabase.getDatabase(application.getApplicationContext());
//        mVINDecodeDao = db.vinDecodeDao();
//        android.util.Log.d("VIN DAO", "getMiles: " + mVINDecodeDao.getAllCars().getValue().toString());
//        mAllCars = mVINDecodeDao.getAllCars();
        List<VINDecode> vinList = new ArrayList<>();
        vinList = mAllCars.getValue();
        android.util.Log.d("REPO", "getMiles: " + vinList.get(0).getCurrent_miles());
        return vinList.get(0).getCurrent_miles();
    }

    public void insert (VINDecode vinDecode) {
        new VINDecodeRepository.insertAsyncTask(mVINDecodeDao).execute(vinDecode);
    }

    public void delete(VINDecode vinDecode){
        new VINDecodeRepository.deleteAsyncTask(mVINDecodeDao).execute(vinDecode);
    }

    public void updateMiles(int miles){
//        mVINDecodeDao.updateMiles(miles);
        new VINDecodeRepository.updateAsyncTask(mVINDecodeDao).execute(miles);
    }

    private static class updateAsyncTask extends AsyncTask<Integer, Void, Void>{
        private VINDecodeDao mAsyncTaskDao;
        updateAsyncTask(VINDecodeDao dao) {
            mAsyncTaskDao = dao;
        }

//        @Override
//        protected Void doInBackground(final int miles) {
//            mAsyncTaskDao.updateMiles(miles);
//            return null;
//        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.updateMiles(integers[0]);
            return null;
        }
    }



    private static class insertAsyncTask extends AsyncTask<VINDecode, Void, Void> {
        private VINDecodeDao mAsyncTaskDao;
        insertAsyncTask(VINDecodeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final VINDecode... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<VINDecode, Void, Void> {
        private VINDecodeDao mAsyncTaskDao;
        deleteAsyncTask(VINDecodeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final VINDecode... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void makeNewsSearchQuery(String vin, int miles){
        Miles = miles;
        URL newsSearchUrl = NetworkUtils.buildUrl(vin);
        new VINQueryTask().execute(newsSearchUrl);
    }

    public class VINQueryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            delete();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String vinSearchResult = null;
            try {
                vinSearchResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return vinSearchResult;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {
            super.onPostExecute(newsSearchResults);
            car = JsonUtils.parseJson(newsSearchResults, Miles).get(0);
            insert(car);
        }
    }

}

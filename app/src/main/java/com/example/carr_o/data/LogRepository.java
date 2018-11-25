package com.example.carr_o.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

public class LogRepository {
    private LogDao mLogDao;
    private LiveData<List<Log>> mAllLogs;
    private LiveData<List<Log>> mSearchLogs;

    public LogRepository(Application application, String search){
        LogRoomDatabase db = LogRoomDatabase.getDatabase(application.getApplicationContext());
        mLogDao = db.logDao();
        mAllLogs = mLogDao.getAllLogs();
        mSearchLogs = mLogDao.getSearch(search);
        android.util.Log.d("REPO_SEARCH", "LogRepository: " + search);

    }

    LiveData<List<Log>> getAllLogs() {
        return mAllLogs;
    }

    LiveData<List<Log>> getSearch(String search){
        return mSearchLogs;
    }

    public void insert (Log log) {
        new insertAsyncTask(mLogDao).execute(log);
    }

    public void delete(Log log){
        new deleteAsyncTask(mLogDao).execute(log);
    }

    private static class insertAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao mAsyncTaskDao;
        insertAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Log, Void, Void> {
        private LogDao mAsyncTaskDao;
        deleteAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Log... params) {
//            android.util.Log.d("mycode", "deleteding word: " + params[0].getWord());
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}

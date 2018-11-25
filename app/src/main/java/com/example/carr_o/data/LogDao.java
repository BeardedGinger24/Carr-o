package com.example.carr_o.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LogDao {

    @Insert
    void insert(Log log);

    @Delete
    void delete(Log log);

    @Query("DELETE FROM log_item")
    void deleteAll();

    @Query("SELECT * FROM log_item ORDER BY maintenance_date ASC")
    LiveData<List<Log>> getAllLogs();

    @Query("SELECT * FROM log_item WHERE maintenance_location LIKE LOWER(:search)")
    LiveData<List<Log>> getSearch(String search);
}

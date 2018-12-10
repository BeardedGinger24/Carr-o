package com.example.carr_o.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.carr_o.data.Log;

import java.util.List;

@Dao
public interface VINDecodeDao {

    @Insert
    void insert(VINDecode vinDecode);

    @Delete
    void delete(VINDecode vinDecode);

    @Query("DELETE FROM car_info")
    void deleteAll();

    @Query("SELECT * FROM car_info ORDER BY id ASC")
    LiveData<List<VINDecode>> getAllCars();
}

package com.example.carr_o.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {VINDecode.class}, version = 1)
public abstract class VINDecodeDatabase extends RoomDatabase {

    public abstract VINDecodeDao vinDecodeDao();

    private static volatile VINDecodeDatabase INSTANCE;

    static VINDecodeDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VINDecodeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VINDecodeDatabase.class, "vin_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

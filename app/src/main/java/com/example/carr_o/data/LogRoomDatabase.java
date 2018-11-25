package com.example.carr_o.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Log.class}, version = 1)
public abstract class LogRoomDatabase extends RoomDatabase {

    public abstract LogDao logDao();

    private static volatile LogRoomDatabase INSTANCE;

    static LogRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LogRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LogRoomDatabase.class, "log_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

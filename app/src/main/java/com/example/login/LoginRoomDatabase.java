package com.example.login;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {LoginMain.class}, version = 2, exportSchema = false)
public abstract class LoginRoomDatabase extends RoomDatabase {

    public abstract LoginDao loginDao();

    private static LoginRoomDatabase INSTANCE;

    public static LoginRoomDatabase getDatabase(final Context context) {

        synchronized (LoginRoomDatabase.class) {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LoginRoomDatabase.class, "login_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}

package com.t2008m.android10.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.t2008m.android10.dao.UserDao;
import com.t2008m.android10.entity.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    public static  AppDatabase appDatabase;
    public abstract UserDao userDao();
    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase==null){
            appDatabase= Room.databaseBuilder(context,AppDatabase.class,"asDatabase").allowMainThreadQueries().build();
        }
        return appDatabase;
    }

}

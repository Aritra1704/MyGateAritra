package com.arpaul.mygate_aritra.dataaccess;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.arpaul.mygate_aritra.daos.UserDao;
import com.arpaul.mygate_aritra.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDB extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserRoomDB INSTANCE;

    public static UserRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDB.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDB.class, "users_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };
}

package com.arpaul.mygate_aritra.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.arpaul.mygate_aritra.models.User;

import java.util.List;

@Dao
public interface UserDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}

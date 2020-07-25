package com.example.shophere;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user_table LIMIT 1")
    User[] getAnyUser();

    @Query("SELECT * FROM user_table ORDER BY username ASC")
    LiveData<List<User>> getAllUser();
}

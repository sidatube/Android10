package com.t2008m.android10.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.t2008m.android10.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);

    @Update
    int updateUser(User user);

    @Delete
    int deleteUser(User user);

    @Query("select * from users")
    List<User> findAll();

    @Query("select * from users where id = :id")
    User findById(int id);
}

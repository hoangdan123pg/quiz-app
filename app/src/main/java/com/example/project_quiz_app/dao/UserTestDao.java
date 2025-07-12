package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.UserTest;

import java.util.List;

@Dao
public interface UserTestDao {
    @Insert long insertTest(UserTest t);
    @Query("SELECT * FROM user_tests WHERE user_id=:uid ORDER BY created_at DESC")
    List<UserTest> listTests(int uid);
    @Delete
    void deleteTest(UserTest t);
}

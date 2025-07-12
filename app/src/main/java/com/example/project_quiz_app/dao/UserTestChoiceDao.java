package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.UserTest;
import com.example.project_quiz_app.model.UserTestChoice;

import java.util.List;

@Dao
public interface UserTestChoiceDao {
    @Insert long insertChoice(UserTestChoice c);
    @Query("SELECT * FROM user_test_choices WHERE question_id=:qid")
    List<UserTestChoice> listChoices(int qid);
    @Delete void deleteChoice(UserTestChoice c);
}
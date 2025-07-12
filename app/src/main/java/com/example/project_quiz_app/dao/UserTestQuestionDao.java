package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.UserTest;
import com.example.project_quiz_app.model.UserTestQuestion;

import java.util.List;

@Dao
public interface UserTestQuestionDao {
    @Insert long insertQuestion(UserTestQuestion q);
    @Query("SELECT * FROM user_test_questions WHERE test_id=:tid")
    List<UserTestQuestion> listQuestions(int tid);
    @Delete void deleteQuestion(UserTestQuestion q);
}
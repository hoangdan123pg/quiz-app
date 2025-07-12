package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Account;
@Dao
public interface AccountDao {
    /**
     * Insert một user mới
     * @param userProfile - Thông tin user cần thêm
     * @return ID của user vừa được thêm (int)
     */
    @Insert
    long insertUser(Account userProfile);
    @Query("SELECT * FROM user_profile WHERE email = :email LIMIT 1")
    Account getAccountByEmail(String email);

    // check login
    @Query("SELECT * FROM user_profile WHERE email = :email AND password = :password LIMIT 1")
    Account checkLogin(String email, String password);

    @Update
    void updateAccount(Account account);
    @Query("UPDATE user_profile SET current_streak = :currentStreak, best_streak = :bestStreak, last_study_date = :lastStudyDate, updated_date = :updatedDate WHERE id = :userId")
    void updateStreak(int userId, int currentStreak, int bestStreak, String lastStudyDate, String updatedDate);
}

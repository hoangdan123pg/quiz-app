package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project_quiz_app.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    long insertCategory(Category category);  // trả về ID của category mới tạo

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    List<Category> getAllCategories(int userId);

}

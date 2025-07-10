package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.project_quiz_app.model.Category;

@Dao
public interface CategoryDao {
    @Insert
    long insertCategory(Category category);  // trả về ID của category mới tạo
}

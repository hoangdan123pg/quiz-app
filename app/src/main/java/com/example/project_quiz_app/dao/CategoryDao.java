package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    long insertCategory(Category category);  // trả về ID của category mới tạo

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    List<Category> getAllCategories(int userId);

    // update category isPublic
    @Query("UPDATE categories SET is_public = :isPublic WHERE id = :categoryId")
    void updateCategoryIsPublic(int categoryId, int isPublic);

    // get category is_publuc
    @Query("SELECT * FROM categories WHERE is_public = 1")
    List<Category> getPublicCategoriesByUser();


    // update category
    @Update
    void updateCategory(Category category);

    // slect categogry by id
    @Query("SELECT * FROM categories WHERE id = :categoryId")
    Category getCategoryById(int categoryId);

}

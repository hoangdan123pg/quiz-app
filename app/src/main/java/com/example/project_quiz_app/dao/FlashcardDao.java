package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project_quiz_app.model.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Insert
    void insertFlashcards(List<Flashcard> flashcards);
    @Query("SELECT * FROM flashcards WHERE category_id = :categoryId")
    List<Flashcard> getFlashcardsByCategoryId(int categoryId);
}

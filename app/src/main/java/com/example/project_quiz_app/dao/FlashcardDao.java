package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.project_quiz_app.model.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Insert
    void insertFlashcards(List<Flashcard> flashcards);
}

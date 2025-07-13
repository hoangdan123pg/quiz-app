package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_quiz_app.model.Category;
import com.example.project_quiz_app.model.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {
    @Insert
    void insertFlashcards(List<Flashcard> flashcards);
    @Query("SELECT * FROM flashcards WHERE category_id = :categoryId")
    List<Flashcard> getFlashcardsByCategoryId(int categoryId);


    // update list flashcard
    @Update
    void updateFlashcard(Flashcard flashcard);
    @Query("SELECT * FROM categories WHERE id = :id")
    Category getCategoryById(int id);

    @Insert
    long insertFlashcard(Flashcard flashcard);
    //update theo id
    @Query("UPDATE flashcards SET term = :term, definition = :definition WHERE id = :id")
    void updateFlashcardById(int id, String term, String definition);

    @Query("DELETE FROM flashcards")
    void deleteAllFlashcards();

}

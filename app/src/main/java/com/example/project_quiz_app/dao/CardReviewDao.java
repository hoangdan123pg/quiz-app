package com.example.project_quiz_app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project_quiz_app.model.CardReview;
@Dao
public interface CardReviewDao {
    // insert card review
    @Insert
    void insertCardReview(CardReview cardReview);
   //  Tìm 1 bản ghi theo flashcardId và userId
    @Query("SELECT * FROM card_reviews WHERE flashcard_id = :flashcardId AND user_id = :userId LIMIT 1")
    CardReview findCardReview(int flashcardId, int userId);

    //update card review
    @Query("UPDATE card_reviews SET is_starred = :isStarred WHERE flashcard_id = :flashcardId AND user_id = :userId")
    void updateCardReview(int flashcardId, int userId, int isStarred);

    @Query("DELETE FROM card_reviews")
    void deleteAllCardReviews();

    @Query("UPDATE card_reviews SET review_type = :reviewType WHERE flashcard_id = :flashcardId AND user_id = :userId")
    void updateReviewType(int flashcardId, int userId, String reviewType);

    @Query("UPDATE card_reviews SET is_starred = :isStarred WHERE flashcard_id = :flashcardId AND user_id = :userId")
    void updateStar(int flashcardId, int userId, int isStarred);

}

package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "card_reviews",
        foreignKeys = {
                @ForeignKey(
                        entity = Account.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Flashcard.class,
                        parentColumns = "id",
                        childColumns = "flashcard_id",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class CardReview {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "flashcard_id")
    private int flashcardId;

    @ColumnInfo(name = "review_type")
    @NonNull
    private String reviewType; // 'correct' hoặc 'incorrect'

    @ColumnInfo(name = "is_starred")
    private int isStarred = 0; // 0 = không gán sao, 1 = gán sao

    // Constructor
    public CardReview(int userId, int flashcardId, @NonNull String reviewType) {
        this.userId = userId;
        this.flashcardId = flashcardId;
        this.reviewType = reviewType;
        this.isStarred = 0;
    }

    // Getter và Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }

    @NonNull
    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(@NonNull String reviewType) {
        this.reviewType = reviewType;
    }

    public int getIsStarred() {
        return isStarred;
    }

    public void setIsStarred(int isStarred) {
        this.isStarred = isStarred;
    }

    @Override
    public String toString() {
        return "CardReview{" +
                "id=" + id +
                ", userId=" + userId +
                ", flashcardId=" + flashcardId +
                ", reviewType='" + reviewType + '\'' +
                ", isStarred=" + isStarred +
                '}';
    }
}

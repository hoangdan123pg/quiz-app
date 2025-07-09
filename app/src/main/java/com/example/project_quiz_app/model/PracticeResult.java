package com.example.project_quiz_app.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "practice_results",
        foreignKeys = {
                @ForeignKey(
                        entity = Account.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )
                // Note: Foreign key cho quiz_questions sẽ được thêm khi bạn tạo model QuizQuestion
        }
)
public class PracticeResult {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "question_id")
    private int questionId;

    @ColumnInfo(name = "user_answer")
    @NonNull
    private String userAnswer;

    @ColumnInfo(name = "is_correct")
    private int isCorrect; // 1 = đúng, 0 = sai

    @ColumnInfo(name = "created_date")
    private String createdDate;

    // Constructor
    public PracticeResult(int userId, int questionId, @NonNull String userAnswer, int isCorrect) {
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @NonNull
    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(@NonNull String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "PracticeResult{" +
                "id=" + id +
                ", userId=" + userId +
                ", questionId=" + questionId +
                ", userAnswer='" + userAnswer + '\'' +
                ", isCorrect=" + isCorrect +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

package com.example.project_quiz_app.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "study_sessions",
        foreignKeys = {
                @ForeignKey(
                        entity = Account.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.SET_NULL
                )
        }
)
public class StudySession {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "study_mode")
    @NonNull
    private String studyMode; // 'flashcard', 'quiz'...

    @ColumnInfo(name = "category_id")
    private Integer categoryId; // có thể null

    @ColumnInfo(name = "total_cards")
    private int totalCards;

    @ColumnInfo(name = "correct_answers")
    private int correctAnswers;

    @ColumnInfo(name = "score_percentage")
    private double scorePercentage;

    @ColumnInfo(name = "created_date")
    private String createdDate;

    // Constructor
    public StudySession(int userId, @NonNull String studyMode, int totalCards, int correctAnswers) {
        this.userId = userId;
        this.studyMode = studyMode;
        this.totalCards = totalCards;
        this.correctAnswers = correctAnswers;
        this.scorePercentage = (totalCards > 0) ? (double) correctAnswers / totalCards * 100 : 0;
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

    @NonNull
    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(@NonNull String studyMode) {
        this.studyMode = studyMode;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "StudySession{" +
                "id=" + id +
                ", userId=" + userId +
                ", studyMode='" + studyMode + '\'' +
                ", categoryId=" + categoryId +
                ", totalCards=" + totalCards +
                ", correctAnswers=" + correctAnswers +
                ", scorePercentage=" + scorePercentage +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

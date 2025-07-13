// model/UserTestChoice.java
package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "user_test_choices",
        foreignKeys = @ForeignKey(
                entity = UserTestQuestion.class,
                parentColumns = "id",
                childColumns = "question_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("question_id")
)
public class UserTestChoice {
    @PrimaryKey(autoGenerate = true) public int id;

    @ColumnInfo(name="question_id") public int questionId;

    @NonNull @ColumnInfo(name="choice_text") public String choiceText;

    @ColumnInfo(name="is_correct") public boolean isCorrect;

    public UserTestChoice(int questionId, @NonNull String choiceText, boolean isCorrect) {
        this.questionId = questionId;
        this.choiceText = choiceText;
        this.isCorrect  = isCorrect;
    }
}

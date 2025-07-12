// model/UserTestQuestion.java
package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "user_test_questions",
        foreignKeys = @ForeignKey(
                entity = UserTest.class,
                parentColumns = "id",
                childColumns = "test_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("test_id")
)
public class UserTestQuestion {
    @PrimaryKey(autoGenerate = true) public int id;

    @ColumnInfo(name="test_id") public int testId;

    @NonNull @ColumnInfo(name="question_text") public String questionText;

    public UserTestQuestion(int testId, @NonNull String questionText) {
        this.testId = testId;
        this.questionText = questionText;
    }
}

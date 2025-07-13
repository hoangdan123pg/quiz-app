// model/UserTest.java
package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "user_tests",
        foreignKeys = @ForeignKey(
                entity = Account.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("user_id")
)
public class UserTest {
    @PrimaryKey(autoGenerate = true) public int id;

    @ColumnInfo(name="user_id") public int userId;

    @NonNull @ColumnInfo(name="title") public String title;

    @ColumnInfo(name="created_at") public String createdAt;

    public UserTest(int userId, @NonNull String title, String createdAt) {
        this.userId = userId;
        this.title = title;
        this.createdAt = createdAt;
    }
}

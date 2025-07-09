package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "flashcards",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "term")
    @NonNull
    private String term;

    @ColumnInfo(name = "definition")
    @NonNull
    private String definition;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    // Constructor
    public Flashcard(@NonNull String term, @NonNull String definition, int categoryId) {
        this.term = term;
        this.definition = definition;
        this.categoryId = categoryId;
    }

    // Getter và Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTerm() {
        return term;
    }

    public void setTerm(@NonNull String term) {
        this.term = term;
    }

    @NonNull
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(@NonNull String definition) {
        this.definition = definition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", definition='" + definition + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}

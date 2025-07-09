package com.example.project_quiz_app.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "categories",
        foreignKeys = @ForeignKey(
                entity = Account.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index(value = {"user_id", "category_name"}, unique = true)}
)
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "category_name")
    @NonNull
    private String categoryName;

    @ColumnInfo(name = "description")
    private String description = "";

    @ColumnInfo(name = "card_count")
    private int cardCount = 0;

    @ColumnInfo(name = "is_public")
    private int isPublic = 0; // 0 = riêng tư, 1 = công khai

    @ColumnInfo(name = "created_date")
    private String createdDate;

    // Constructor
    public Category(int userId, @NonNull String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.description = "";
        this.cardCount = 0;
        this.isPublic = 0;
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
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", userId=" + userId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", cardCount=" + cardCount +
                ", isPublic=" + isPublic +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}

package com.example.project_quiz_app.model;

public class FlashcardHeaderItem {
    private String categoryName;
    private String description;

    public FlashcardHeaderItem(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }
}

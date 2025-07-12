package com.example.project_quiz_app.model;

public class FlashcardHeaderItem {
    private String categortyTitle;
    private String categoryDescription;
    private String descriptionForAI;

    public FlashcardHeaderItem(String categortyTitle, String categoryDescription, String descriptionForAI) {
        this.categortyTitle = categortyTitle;
        this.categoryDescription = categoryDescription;
        this.descriptionForAI = descriptionForAI;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getDescriptionForAI() {
        return descriptionForAI;
    }

    public void setDescriptionForAI(String descriptionForAI) {
        this.descriptionForAI = descriptionForAI;
    }

    public String getCategortyTitle() {
        return categortyTitle;
    }

    public void setCategortyTitle(String categortyTitle) {
        this.categortyTitle = categortyTitle;
    }
}

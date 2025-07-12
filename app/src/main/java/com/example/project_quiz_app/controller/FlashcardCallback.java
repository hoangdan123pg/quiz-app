package com.example.project_quiz_app.controller;

public interface FlashcardCallback {
    void onFlashcardFlipped(int flashcardId);
    void onFlashcardStarred(int flashcardId);
}

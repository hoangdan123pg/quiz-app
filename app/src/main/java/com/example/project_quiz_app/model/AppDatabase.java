package com.example.project_quiz_app.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project_quiz_app.dao.AccountDao;
import com.example.project_quiz_app.dao.CardReviewDao;
import com.example.project_quiz_app.dao.CategoryDao;
import com.example.project_quiz_app.dao.FlashcardDao;
import com.example.project_quiz_app.dao.UserTestChoiceDao;
import com.example.project_quiz_app.dao.UserTestDao;
import com.example.project_quiz_app.dao.UserTestQuestionDao;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.Category;
import com.example.project_quiz_app.model.Flashcard;


@Database(entities = {Account.class, Category.class, Flashcard.class, CardReview.class, UserTest.class,              // ← add this
        UserTestQuestion.class,
        UserTestChoice.class}, version = 6, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {

        public abstract AccountDao accountDao();
        public abstract CategoryDao categoryDao();
        public abstract FlashcardDao flashcardDao();
        public abstract CardReviewDao cardReviewDao();
        public abstract UserTestDao userTestDao();
        public abstract UserTestQuestionDao userTestQuestionDao();
        public abstract UserTestChoiceDao userTestChoiceDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "database.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

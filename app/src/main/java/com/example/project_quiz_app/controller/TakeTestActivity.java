package com.example.project_quiz_app.controller;

import android.os.Bundle;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.UserTestChoice;
import com.example.project_quiz_app.model.UserTestQuestion;
import com.example.project_quiz_app.view.fragments.TakeTestFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TakeTestActivity extends AppCompatActivity {
    public interface TakeTestListener {
        @UiThread void onQuestionsLoaded(
                List<UserTestQuestion> questions,
                Map<Integer, List<UserTestChoice>> choicesMap
        );
    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private AppDatabase db;
    private TakeTestListener listener;
    private int testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);

        db = AppDatabase.getInstance(this);
        testId = getIntent().getIntExtra("testId", -1);

        if (savedInstanceState == null) {
            Fragment frag = new TakeTestFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.take_test_fragment_container, frag)
                    .commit();
        }
    }

    /** Called by fragment to load all questions & their choices */
    public void loadTestData(TakeTestListener l) {
        listener = l;
        executor.execute(() -> {
            List<UserTestQuestion> questions =
                    db.userTestQuestionDao().listQuestions(testId);

            Map<Integer, List<UserTestChoice>> map = new HashMap<>();
            for (UserTestQuestion q : questions) {
                List<UserTestChoice> choices =
                        db.userTestChoiceDao().listChoices(q.id);
                map.put(q.id, choices);
            }

            runOnUiThread(() -> listener.onQuestionsLoaded(questions, map));
        });
    }
}

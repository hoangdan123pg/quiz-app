package com.example.project_quiz_app.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.UserTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.view.fragments.PracticeFragment;

public class PracticeActivity extends AppCompatActivity {
    public interface PracticeListener {
        @UiThread void onTestsLoaded(List<UserTest> tests);
        @UiThread void onTestCreated();
    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private PracticeListener listener;
    private AppDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        // read logged‑in user
        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        userId = Integer.parseInt(prefs.getString("user_id", "0"));

        db = AppDatabase.getInstance(this);

        // host the fragment
        if (savedInstanceState == null) {
            PracticeFragment frag = new PracticeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.practice_fragment_container, frag)
                    .commit();
        }
    }

    /** Called by fragment to load all tests */
    public void loadTests(PracticeListener l) {
        listener = l;
        executor.execute(() -> {
            List<UserTest> tests = db.userTestDao().listTests(userId);
            runOnUiThread(() -> listener.onTestsLoaded(tests));
        });
    }

    /** Called by fragment to create a new test with a title */
    public void createTest(String title) {
        executor.execute(() -> {
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    .format(new Date());
            UserTest t = new UserTest(userId, title, now);
            db.userTestDao().insertTest(t);
            runOnUiThread(() -> listener.onTestCreated());
        });
    }

    /** Launch the “take test” screen (not shown here) */
    public void takeTest(int testId) {
        Intent i = new Intent(this, TakeTestActivity.class);
        i.putExtra("testId", testId);
        startActivity(i);
    }
}

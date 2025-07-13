package com.example.project_quiz_app.controller;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_quiz_app.dao.AccountDao;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.view.fragments.ProfileFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.project_quiz_app.R;

public class ProfileActivity extends AppCompatActivity {
    public interface ProfileListener {
        @UiThread void onProfileLoaded(Account account);
        @UiThread void onProfileUpdated();
    }

    private AccountDao accountDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 1) grab the logged-in email from prefs
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        userEmail = prefs.getString("loggedInEmail", null);

        // 2) init DAO
        accountDao = AppDatabase.getInstance(this).accountDao();

        // 3) load the fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.profile_fragment_container, new ProfileFragment())
                    .commit();
        }
    }

    /** Called by the Fragment to fetch the Account in background */
    public void loadProfile(ProfileListener listener) {
        executor.execute(() -> {
            Account user = accountDao.getAccountByEmail(userEmail);
            runOnUiThread(() -> listener.onProfileLoaded(user));
        });
    }

    /** Called by the Fragment to save changes */
    public void updateProfile(Account updated, ProfileListener listener) {
        executor.execute(() -> {
            accountDao.updateAccount(updated);
            runOnUiThread(listener::onProfileUpdated);
        });
    }
}

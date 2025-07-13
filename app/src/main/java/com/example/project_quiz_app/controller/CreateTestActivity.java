package com.example.project_quiz_app.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.view.fragments.CreateTestFragment;

public class CreateTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_test_fragment_container, new CreateTestFragment())
                    .commit();
        }
    }
}

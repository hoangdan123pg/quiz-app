package com.example.project_quiz_app.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_quiz_app.R;

public class ActivityFlashCard extends AppCompatActivity {

    private TextView tvId ;

    private void bindingView(){
        tvId = findViewById(R.id.textView);
    }

    private void bindingAction(){
        getDataFromIntent();
    }

    // lay data tu intent
    private void getDataFromIntent(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("category_id", -1);
        tvId.setText("Lât thẻ ở đây category_id : " +id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}
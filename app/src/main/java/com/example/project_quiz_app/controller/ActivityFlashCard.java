package com.example.project_quiz_app.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;

import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Flashcard;
import com.example.project_quiz_app.model.FlashcardItem;

import com.example.project_quiz_app.view.adapters.CreateFlashcardAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ActivityFlashCard extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView rcFlashcard;
    private Button btnAI;
    private List<FlashcardItem> flashcardItems = new ArrayList<>();
    private CreateFlashcardAdapter adapter;
    private String categoryName, categoryDescription;
    private int userId;
    private void bindingView() {
        rcFlashcard = findViewById(R.id.recyclerView);
        btnAI = findViewById(R.id.btnAI);
        db = AppDatabase.getInstance(this);
        // Lấy thông tin từ sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            return;
        }
        userId = Integer.parseInt(userIdStr);
    }

    private void bindingAction() {
        getDataFromIntent();
        btnAI.setOnClickListener(this::showAIBottomSheet);
    }

    private void initRecyclerView() {
        adapter = new CreateFlashcardAdapter(flashcardItems);
        rcFlashcard.setAdapter(adapter);
        rcFlashcard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    // Implement callback methods
//    @Override
//    public void onFlashcardFlipped(int flashcardId) {
//        // Lưu bản ghi review khi flip thẻ
//        insertFlashcardReview(flashcardId, "viewed", false);
//    }
//
//    @Override
//    public void onFlashcardStarred(int flashcardId) {
//        // Lưu bản ghi review khi star thẻ
//        insertFlashcardReview(flashcardId, "starred", true);
//    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("category_id", -1);
        categoryName= intent.getStringExtra("category_name");
        categoryDescription= intent.getStringExtra("category_description");


        new Thread(() -> {
            // Lấy dữ liệu từ DB
            List<Flashcard> flashcards = db.flashcardDao().getFlashcardsByCategoryId(id);

            // Chuyển đổi sang FlashcardItem
            List<FlashcardItem> items = new ArrayList<>();
            for (Flashcard flashcard : flashcards) {
                items.add(new FlashcardItem( flashcard.getId(), flashcard.getTerm(), flashcard.getDefinition()));
            }

            // Cập nhật UI
            runOnUiThread(() -> {
                flashcardItems.clear();
                flashcardItems.addAll(items);
                initRecyclerView();
            });
        }).start();
    }

    private void showAIBottomSheet(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ai_bottom_sheet, null);
        dialog.setContentView(view);

        // lay data tu recyclerview
        // Lấy vị trí đầu tiên đang hiển thị
        LinearLayoutManager layoutManager = (LinearLayoutManager) rcFlashcard.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();  // hoặc lastVisibleItemPosition()

        // Lấy data từ adapter
        FlashcardItem item = adapter.getItemByPosition(position);
        // Ví dụ: hiển thị log
        Log.d("FlashcardData", "Term: " + item.getTerm() + ", Definition: " + item.getDefinition() + "\n Title: " + categoryName + "description: " + categoryDescription);

        // Thiết lập chiều cao 70% màn hình
        View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            bottomSheet.getLayoutParams().height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
        }
        dialog.show();
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

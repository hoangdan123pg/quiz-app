package com.example.project_quiz_app.controller;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Category;

import java.util.List;

public class LearnCollectionActivity extends AppCompatActivity {
    private Button btnBack;
    private AppDatabase db;
    private LinearLayout llCategories;
    List<Category> listCategories;
    private void bindingView() {
        // Khởi tạo Room Database
        db = AppDatabase.getInstance(this);
        btnBack = findViewById(R.id.btnBack);
        llCategories = findViewById(R.id.llCategories);

        // Lấy thông tin từ sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            return;
        }
        listCategories = db.categoryDao().getAllCategories(parseInt(userIdStr));
        Log.d("Category Log", listCategories.toString());
    }
    private void bindingAction() {
        btnBack.setOnClickListener(view -> finish());
        loadCategories();
    }
    private void loadCategories() {
        // thêm vào LinearLayout
        llCategories.removeAllViews(); // Xóa các category cũ nếu có
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Category category : listCategories) {
            // Inflate layout item_category.xml
            View categoryView = inflater.inflate(R.layout.item_category, llCategories, false);

            // Ánh xạ các view bên trong item_category.xml
            LinearLayout itemRoot = categoryView.findViewById(R.id.itemRoot);
            TextView tvCategoryName = categoryView.findViewById(R.id.tvCategoryName);
            TextView tvCount = categoryView.findViewById(R.id.tvCount);
            // Set dữ liệu cho category
            tvCategoryName.setText(category.getCategoryName());
            tvCount.setText("Tổng số thẻ: "+category.getCardCount());

            // Thêm onClick nếu cần
            itemRoot.setOnClickListener(v -> {
                Toast.makeText(this, "Click: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
                // Hoặc mở activity khác
                // Tạo Intent để mở Activity mới
                Intent intent = new Intent(this, ActivityFlashCard.class);

                // Truyền category_id theo key là "category_id"
                intent.putExtra("category_id", category.getId());
                intent.putExtra("category_name", category.getCategoryName());
                intent.putExtra("category_description", category.getDescription());
                // Mở Activity
                startActivity(intent);
            });
            // Thêm view vào LinearLayout
            llCategories.addView(categoryView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_collection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}
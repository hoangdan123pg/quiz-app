package com.example.project_quiz_app.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;

import com.example.project_quiz_app.dao.CategoryDao;
import com.example.project_quiz_app.dao.FlashcardDao;
import com.example.project_quiz_app.model.AppDatabase;

import com.example.project_quiz_app.model.Category;
import com.example.project_quiz_app.model.Flashcard;
import com.example.project_quiz_app.model.FlashcardHeaderItem;
import com.example.project_quiz_app.model.FlashcardItem;
import com.example.project_quiz_app.view.adapters.CreateCardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class UpdateFlashCard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd, fab_submit;
    private List<FlashcardItem> items;
    private FlashcardHeaderItem header;
    private CreateCardAdapter adapterUpdate;
    private AppDatabase db;

    private void bindingView() {
        recyclerView = findViewById(R.id.recyclerViewUpdate);
        fabAdd = findViewById(R.id.fab_add);
        fab_submit = findViewById(R.id.fab_submit);

        adapterUpdate = new CreateCardAdapter(header, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //
        recyclerView.setAdapter(adapterUpdate);
    }

    private void bindingAction() {
        fabAdd.setOnClickListener(this::onClickAddItem);
        fab_submit.setOnClickListener(this::onClickUpdate);
    }
    private void onClickAddItem(View view){
        items.add(new FlashcardItem(0,"", ""));
        // Gọi hàm để thông báo RecyclerView cập nhật
        adapterUpdate.notifyItemInserted(items.size()); // vì có header nên vị trí là size hiện tại
        recyclerView.scrollToPosition(items.size()); // Cuộn đến item mới
    }
    private void onClickUpdate(View view) {
        // Lấy dữ liệu mới
        FlashcardHeaderItem updatedHeader = adapterUpdate.getUpdatedHeader();
        List<FlashcardItem> updatedItems = adapterUpdate.getUpdatedItems();
        // cap nhat lai du lieu theo _id
        for (int i = 0; i < updatedItems.size(); i++) {
            updatedItems.get(i).setId(items.get(i).getId());
        }
        // Lấy user_id từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            Toast.makeText(this, "User chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = Integer.parseInt(userIdStr);
        // Tạo database và DAO
        CategoryDao categoryDao = db.categoryDao();
        FlashcardDao flashcardDao = db.flashcardDao();
        // lay id tu intent
        int categoryId = getIntent().getIntExtra("category_id", -1);
        if (categoryId == -1) {
            Toast.makeText(this, "Không tìm thấy Category ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            //CategoryDao categoryDao = db.categoryDao();
           // FlashcardDao flashcardDao = db.flashcardDao();

            // 1. Lấy category từ DB
            Category category = categoryDao.getCategoryById(categoryId);
            if (category == null) return;

            // 2. Cập nhật nội dung mới
            category.setCategoryName(updatedHeader.getCategortyTitle());
            category.setDescription(updatedHeader.getCategoryDescription() + "\nAI: " + updatedHeader.getDescriptionForAI());
            category.setCardCount(updatedItems.size());

            // 3. Update Category
            categoryDao.updateCategory(category);

            // 4. Update từng Flashcard
            for (FlashcardItem item : updatedItems) {
                Log.d("UpdateFlashCard-h", "Data" + "id: " +item.getId() + "-term: " + item.getTerm() +"def"+ item.getDefinition());
                if (item.getId() != 0) {
                    // Flashcard cũ → update
                    flashcardDao.updateFlashcardById(item.getId(), item.getTerm(), item.getDefinition());
                    //Log.d("UpdateFlashCard-h", "Flashcard cũ → update: " + item.getId() + item.getTerm() + item.getDefinition());
                    Log.d("UpdateFlashCard-h", "Flashcard cũ → update: " + "id: " +item.getId() + "-term: " + item.getTerm() +"def"+ item.getDefinition());
                } else {
                    // Flashcard mới → insert
                    Flashcard newFlashcard = new Flashcard(item.getTerm(), item.getDefinition(), categoryId);
                    flashcardDao.insertFlashcard(newFlashcard); // insert từng cái
                    //Log.d("UpdateFlashCard-h", "Flashcard mới → insert" + item.getId() + item.getTerm() + item.getDefinition());
                    Log.d("UpdateFlashCard-h", "Flashcard mới → insert: " + "id: " +item.getId() + "-term: " + item.getTerm() +"def"+ item.getDefinition());
                }
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish(); // hoặc quay về màn trước
            });
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initSampleData();
        bindingView();
        bindingAction();
    }
    // Khởi tạo dữ liệu mẫu
    private void initSampleData() {
        // khoi tao db
        db = AppDatabase.getInstance(this);
        // lay thong tin tu intent
        int categoryId = getIntent().getIntExtra("category_id", -1); // -1 là mặc định nếu không có
        // lay thong tin tu category tu db
        Category header_raw = db.categoryDao().getCategoryById(categoryId);
        String title = header_raw.getCategoryName();
        String raw = header_raw.getDescription();
        String[] parts = raw.split("\nAI:", 2);
        String des = parts.length > 0 ? parts[0].trim() : "";
        String aiHint = parts.length > 1 ? parts[1].trim() : "";
        header = new FlashcardHeaderItem(header_raw.getCategoryName(), des, aiHint);
        // lay thong tin flashcard
        List<Flashcard> flashcards = db.flashcardDao().getFlashcardsByCategoryId(categoryId);
        items = new ArrayList<>();
        for (Flashcard flashcard : flashcards) {
            items.add(new FlashcardItem(flashcard.getId(), flashcard.getTerm(), flashcard.getDefinition()));
            Log.d("UpdateFlashCard-h", "Flashcard Loading: " + "id: " +flashcard.getId() + "-term: " + flashcard.getTerm() +"def"+ flashcard.getDefinition());
        }
    }
    private void delete(View v) {
        db.flashcardDao().deleteAllFlashcards();
    }
}

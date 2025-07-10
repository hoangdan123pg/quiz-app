package com.example.project_quiz_app.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class CreateFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd, fab_submit;
    private List<FlashcardItem> items;
    private FlashcardHeaderItem header;
    private CreateCardAdapter adapter;
    private AppDatabase db;

    public CreateFragment() {
        // Constructor rỗng bắt buộc
    }
    private void bindingView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fab_add);
        fab_submit = view.findViewById(R.id.fab_submit);

        adapter = new CreateCardAdapter(header, items);
        //adapter.attachRecyclerView(recyclerView); // thêm dòng này
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //db
        db = AppDatabase.getInstance(requireContext());
    }
    private void bindingAction(){
        fabAdd.setOnClickListener(this::onClickAddItem);
        fab_submit.setOnClickListener(this::onClickSubmit);
    }

    private void onClickAddItem(View view){
        items.add(new FlashcardItem("", ""));
        // Gọi hàm để thông báo RecyclerView cập nhật
        adapter.notifyItemInserted(items.size()); // vì có header nên vị trí là size hiện tại
        recyclerView.scrollToPosition(items.size()); // Cuộn đến item mới
    }
    private void onClickSubmit(View view){
        // Lấy dữ liệu từ Adapter
        FlashcardHeaderItem updatedHeader = adapter.getUpdatedHeader();
        List<FlashcardItem> updatedItems = adapter.getUpdatedItems();

        // Lấy user_id từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            Toast.makeText(getContext(), "User chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = Integer.parseInt(userIdStr);

        // Tạo database và DAO
        CategoryDao categoryDao = db.categoryDao();
        FlashcardDao flashcardDao = db.flashcardDao();

        // Chạy trên background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            // 1. Thêm Category
            Category category = new Category(userId, updatedHeader.getCategortyTitle());
            category.setDescription(updatedHeader.getCategoryDescription() + "\nAI: " + updatedHeader.getDescriptionForAI());
            category.setCardCount(updatedItems.size());
            category.setCreatedDate(String.valueOf(System.currentTimeMillis()));  // Lưu timestamp hiện tại

            long categoryId = categoryDao.insertCategory(category);

            // 2. Thêm các Flashcard
            List<Flashcard> flashcards = new ArrayList<>();
            for (FlashcardItem item : updatedItems) {
                flashcards.add(new Flashcard(item.getTerm(), item.getDefinition(), (int) categoryId));
            }

            flashcardDao.insertFlashcards(flashcards);

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        initSampleData();      // Tách dữ liệu mẫu
        bindingView(view);     // Gán view
        bindingAction();       // Gán sự kiện
        return view;
    }
    // Khởi tạo dữ liệu mẫu
    private void initSampleData() {
        header = new FlashcardHeaderItem("","", "");
        items = new ArrayList<>();
        items.add(new FlashcardItem("", ""));
        items.add(new FlashcardItem("", ""));
    }
}

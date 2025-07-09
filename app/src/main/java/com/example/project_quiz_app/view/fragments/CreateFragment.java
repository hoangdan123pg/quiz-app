package com.example.project_quiz_app.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.FlashcardHeaderItem;
import com.example.project_quiz_app.model.FlashcardItem;
import com.example.project_quiz_app.view.adapters.CreateCardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CreateFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private List<FlashcardItem> items;
    private FlashcardHeaderItem header;
    private CreateCardAdapter adapter;

    public CreateFragment() {
        // Constructor rỗng bắt buộc
    }
    private void bindingView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fab_add);

        adapter = new CreateCardAdapter(header, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    private void bindingAction(){
        fabAdd.setOnClickListener(this::onClickAddItem);

    }

    private void onClickAddItem(View view){
        items.add(new FlashcardItem("", ""));
        // Gọi hàm để thông báo RecyclerView cập nhật
        adapter.notifyItemInserted(items.size()); // vì có header nên vị trí là size hiện tại
        recyclerView.scrollToPosition(items.size()); // Cuộn đến item mới
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
        header = new FlashcardHeaderItem("Tên chủ đề", "Mô tả");
        items = new ArrayList<>();
        items.add(new FlashcardItem("Question 1", "Answer 1"));
        items.add(new FlashcardItem("Question 2", "Answer 2"));
    }
}

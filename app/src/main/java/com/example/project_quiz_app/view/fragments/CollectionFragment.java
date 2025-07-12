package com.example.project_quiz_app.view.fragments;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.ActivityFlashCard;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Category;

import java.util.List;

public class CollectionFragment extends Fragment {

    private AppDatabase db;
    private LinearLayout llCategories;
    List<Category> listCategories;

    public CollectionFragment() {
        // Constructor rỗng bắt buộc
    }
    private void bindingView(View view) {
        // Khởi tạo Room Database
        db = AppDatabase.getInstance(requireContext());
        llCategories = view.findViewById(R.id.llCategories);
        // Lấy thông tin từ sharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_id", null);
        if (userIdStr == null) {
            return;
        }
        listCategories = db.categoryDao().getAllCategories(parseInt(userIdStr));
        Log.d("Category Log", listCategories.toString());
    }
    private void bindingAction(){
        loadCategories();
    }
    private void loadCategories() {
        // thêm vào LinearLayout
        llCategories.removeAllViews(); // Xóa các category cũ nếu có
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        for (Category category : listCategories) {
            // Inflate layout item_category.xml
            View categoryView = inflater.inflate(R.layout.item_category_manager, llCategories, false);

            // Ánh xạ các view bên trong item_category.xml
            LinearLayout itemRoot = categoryView.findViewById(R.id.itemRoot1);
            TextView tvCategoryName = categoryView.findViewById(R.id.tvCategoryName1);
            TextView tvCount = categoryView.findViewById(R.id.tvCount1);
            Switch swCategory = categoryView.findViewById(R.id.switch1);
            // Set dữ liệu cho category
            tvCategoryName.setText(category.getCategoryName());
            tvCount.setText("Tổng số thẻ: "+category.getCardCount());
            if (category.getIsPublic() == 1) {
                swCategory.setChecked(true);
            } else {
                swCategory.setChecked(false);
            }

            // Thêm onClick nếu cần
            itemRoot.setOnClickListener(v -> {
                Toast.makeText(requireContext() , "Click: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
                // Hoặc mở activity khác

            });
            // Thêm view vào LinearLayout
            llCategories.addView(categoryView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        bindingView(view);
        bindingAction();
        return view;
    }
}
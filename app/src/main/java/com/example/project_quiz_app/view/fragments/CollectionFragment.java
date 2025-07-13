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
import com.example.project_quiz_app.controller.UpdateFlashCard;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Category;

import java.util.List;

public class CollectionFragment extends Fragment {

    private AppDatabase db;
    private LinearLayout llCategories;
    List<Category> listCategories;

//    private Switch swCategory;

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

//        // Thêm onClick cho switch
//        swCategory = view.findViewById(R.id.switch1);
    }
    private void bindingAction(){
        loadCategories();
//        swCategory.setOnClickListener(this::onSwitchCheckedChanged);
    }
    private void loadCategories() {
        // thêm vào LinearLayout
        llCategories.removeAllViews(); // Xóa các category cũ nếu có
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        for (Category category : listCategories) {
            final Category currentCategory = category; // giữ đúng context trong lambda

            View categoryView = inflater.inflate(R.layout.item_category_manager, llCategories, false);

            LinearLayout itemRoot = categoryView.findViewById(R.id.itemRoot1);
            TextView tvCategoryName = categoryView.findViewById(R.id.tvCategoryName1);
            TextView tvCount = categoryView.findViewById(R.id.tvCount1);
            Switch swCategory = categoryView.findViewById(R.id.switch1);

            tvCategoryName.setText(currentCategory.getCategoryName());
            tvCount.setText("Tổng số thẻ: " + currentCategory.getCardCount());
            swCategory.setChecked(currentCategory.getIsPublic() == 1);

            itemRoot.setOnClickListener(v -> {
                //Toast.makeText(requireContext(), "Click: " + currentCategory.getCategoryName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), UpdateFlashCard.class);
                  intent.putExtra("category_id", currentCategory.getId());
               // intent.putExtra("category_name", currentCategory.getCategoryName());
                startActivity(intent);
            });

            swCategory.setOnClickListener(v -> {
                boolean isChecked = swCategory.isChecked();
                int isPublic = isChecked ? 1 : 0;

                Toast.makeText(requireContext(), "Switch is " + (isChecked ? "ON" : "OFF") + " for category: " + currentCategory.getId(), Toast.LENGTH_SHORT).show();

                new Thread(() -> {
                    db.categoryDao().updateCategoryIsPublic(currentCategory.getId(), isPublic);
                }).start();
            });

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
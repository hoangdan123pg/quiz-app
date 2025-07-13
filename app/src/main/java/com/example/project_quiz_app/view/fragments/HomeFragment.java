package com.example.project_quiz_app.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.ActivityFlashCard;
import com.example.project_quiz_app.controller.LearnCollectionActivity;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.Category;

import java.util.List;
import com.example.project_quiz_app.controller.MainActivity;
import com.example.project_quiz_app.controller.PracticeActivity;
import com.example.project_quiz_app.controller.ProfileActivity;

public class HomeFragment extends Fragment {
    private LinearLayout llLearn, llPractice, layout_course_list;
    private CardView cardPractice;
    private TextView tvGreeting, tvStreakText;
    private ImageView ivHomeAvatar;

    private AppDatabase appDatabase;
    List<Category> listCategories; // Danh sách category>

    private void bindingView(View view) {
        llLearn = view.findViewById(R.id.llLearn);
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvStreakText = view.findViewById(R.id.tvStreakText);
        layout_course_list = view.findViewById(R.id.layout_course_list);

        // Khởi tạo Room Database
        appDatabase = AppDatabase.getInstance(requireContext());
        cardPractice = view.findViewById(R.id.cardPractice);
        ivHomeAvatar = view.findViewById(R.id.ivAvatar);

        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String avatarUri = prefs.getString("user_avatar", "");
        if (!TextUtils.isEmpty(avatarUri)) {
            ivHomeAvatar.setImageURI(Uri.parse(avatarUri));
        }
    }
    private void bindingAction() {
        // Lấy user_id từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("user_name", null);
        int streak = sharedPreferences.getInt("user_current_streak", 0);
        if (userIdStr == null) {
            return;
        }

        llLearn.setOnClickListener(this::onClickLearn);
        cardPractice.setOnClickListener(this::onClickPractice);
        tvGreeting.setText("Hello, " + userIdStr);
        tvStreakText.setText("Current Streak: " + streak);
        // Load danh sách category
        loadCategories();
    }
    private void loadCategories() {
        List<Category> listCategories = appDatabase.categoryDao().getPublicCategoriesByUser();
        // Thêm category với LinearLayout
        layout_course_list.removeAllViews(); // Xóa caregory cũ nếu có
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        for (Category category : listCategories) {
            final Category currentCategory = category; // để dùng trong lambda

            new Thread(() -> {
                // Lấy user name trong background
                String userName = appDatabase.accountDao().getUserNameById(currentCategory.getUserId());

                // Quay lại luồng chính để cập nhật giao diện
                requireActivity().runOnUiThread(() -> {
                    View categoryView = inflater.inflate(R.layout.item_category_home, layout_course_list, false);
                    LinearLayout itemRoot = categoryView.findViewById(R.id.itemRoot1);
                    TextView tvCategoryName = categoryView.findViewById(R.id.tvCategoryName1);
                    TextView tvCount = categoryView.findViewById(R.id.tvCount1);

                    tvCategoryName.setText(currentCategory.getCategoryName() + " - " + userName);
                    tvCount.setText("Tổng số thẻ: " + currentCategory.getCardCount());

                    layout_course_list.addView(categoryView);

                    itemRoot.setOnClickListener(v -> {
                        // Toast.makeText(requireContext(), "Click: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
                        // mo activity flashcard
                        Intent intent = new Intent(requireContext(), ActivityFlashCard.class);
                        intent.putExtra("category_id", category.getId());
                        intent.putExtra("category_name", category.getCategoryName());
                        intent.putExtra("category_description", category.getDescription());
                        startActivity(intent);
                    });
                });
            }).start();
        }

        ivHomeAvatar.setOnClickListener(this::onClickHomeAvatar);
    }

    private void onClickHomeAvatar(View view) {
        ((MainActivity) requireActivity()).selectProfileTab();
    }

    private void onClickPractice(View view) {
        Intent intent = new Intent(getActivity(), PracticeActivity.class);
        startActivity(intent);
    }

    private void onClickLearn(View view) {
        // Chuyen sang activity LearnCollectionActivity
        Intent intent = new Intent(getActivity(), LearnCollectionActivity.class);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bindingView(view);
        bindingAction();
        return view;
    }
}
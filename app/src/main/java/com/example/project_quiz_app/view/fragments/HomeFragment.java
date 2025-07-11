package com.example.project_quiz_app.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.LearnCollectionActivity;

public class HomeFragment extends Fragment {
    private LinearLayout llLearn, llPractice;
    private TextView tvGreeting, tvStreakText;

    private void bindingView(View view) {
        llLearn = view.findViewById(R.id.llLearn);
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvStreakText = view.findViewById(R.id.tvStreakText);
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
        tvGreeting.setText("Hello, " + userIdStr);
        tvStreakText.setText("Current Streak: " + streak);
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
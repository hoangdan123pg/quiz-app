package com.example.project_quiz_app.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.LearnCollectionActivity;
import com.example.project_quiz_app.controller.PracticeActivity;

public class HomeFragment extends Fragment {
    private LinearLayout llLearn, llPractice;
    private CardView cardPractice;
    private TextView tvGreeting, tvStreakText;

    private void bindingView(View view) {
        llLearn = view.findViewById(R.id.llLearn);
        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvStreakText = view.findViewById(R.id.tvStreakText);
        cardPractice = view.findViewById(R.id.cardPractice);
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
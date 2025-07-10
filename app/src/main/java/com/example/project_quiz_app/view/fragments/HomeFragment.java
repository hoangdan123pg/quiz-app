package com.example.project_quiz_app.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.LearnCollectionActivity;

public class HomeFragment extends Fragment {
    private LinearLayout llLearn, llPractice;

    private void bindingView(View view) {
        llLearn = view.findViewById(R.id.llLearn);
    }
    private void bindingAction() {
        llLearn.setOnClickListener(this::onClickLearn);
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
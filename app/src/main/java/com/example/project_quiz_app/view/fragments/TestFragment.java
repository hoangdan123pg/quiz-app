package com.example.project_quiz_app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;

public class TestFragment extends Fragment {

    public TestFragment() {
        // Constructor rỗng bắt buộc
    }
    private void bindingView(View view) {}
    private void bindingAction(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        bindingView(view);
        bindingAction();
        return view;
    }
}

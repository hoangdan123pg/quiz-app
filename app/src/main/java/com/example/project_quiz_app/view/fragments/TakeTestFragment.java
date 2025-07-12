package com.example.project_quiz_app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.TakeTestActivity;
import com.example.project_quiz_app.model.UserTestChoice;
import com.example.project_quiz_app.model.UserTestQuestion;

import java.util.List;
import java.util.Map;

public class TakeTestFragment extends Fragment implements TakeTestActivity.TakeTestListener {
    private TextView tvQuestion;
    private RadioGroup rgChoices;
    private Button btnNext;

    private List<UserTestQuestion> questions;
    private Map<Integer, List<UserTestChoice>> choicesMap;
    private int currentIndex = 0;
    private int correctCount = 0;

    private void bindingView(View v) {
        tvQuestion = v.findViewById(R.id.tv_question);
        rgChoices  = v.findViewById(R.id.rg_choices);
        btnNext    = v.findViewById(R.id.btn_next);
    }

    private void bindingAction() {
        // Load data
        ((TakeTestActivity) requireActivity()).loadTestData(this);

        btnNext.setOnClickListener(c -> {
            int selectedId = rgChoices.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rb = rgChoices.findViewById(selectedId);
            boolean isCorrect = Boolean.parseBoolean(
                    rb.getTag().toString()
            );
            if (isCorrect) correctCount++;

            currentIndex++;
            if (currentIndex < questions.size()) {
                showQuestion(currentIndex);
            } else {
                // Finished
                Toast.makeText(
                        getContext(),
                        "You got " + correctCount +
                                " out of " + questions.size(),
                        Toast.LENGTH_LONG
                ).show();
                requireActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle s) {
        View v = inflater.inflate(R.layout.fragment_take_test, c, false);
        bindingView(v);
        bindingAction();
        return v;
    }

    @Override
    public void onQuestionsLoaded(
            List<UserTestQuestion> qs,
            Map<Integer, List<UserTestChoice>> map
    ) {
        this.questions  = qs;
        this.choicesMap = map;
        if (!qs.isEmpty()) {
            showQuestion(0);
        }
    }

    private void showQuestion(int index) {
        UserTestQuestion q = questions.get(index);
        tvQuestion.setText((index + 1) + ". " + q.questionText);

        rgChoices.removeAllViews();
        for (UserTestChoice c : choicesMap.get(q.id)) {
            RadioButton rb = new RadioButton(requireContext());
            rb.setText(c.choiceText);
            rb.setTag(c.isCorrect);  // store correctness
            rgChoices.addView(rb);
        }
        rgChoices.clearCheck();
    }
}

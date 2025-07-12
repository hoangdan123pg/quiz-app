package com.example.project_quiz_app.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.CreateTestActivity;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.UserTest;
import com.example.project_quiz_app.model.UserTestChoice;
import com.example.project_quiz_app.model.UserTestQuestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CreateTestFragment extends Fragment {
    private EditText etTitle;
    private Button btnAddQuestion, btnSaveTest;
    private LinearLayout containerQuestions;

    private Executor executor = Executors.newSingleThreadExecutor();
    private AppDatabase db;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inf, ViewGroup c, Bundle b) {
        View v = inf.inflate(R.layout.fragment_create_test, c, false);
        etTitle            = v.findViewById(R.id.et_test_title);
        btnAddQuestion     = v.findViewById(R.id.btn_add_question);
        btnSaveTest        = v.findViewById(R.id.btn_save_test);
        containerQuestions = v.findViewById(R.id.container_questions);

        db = AppDatabase.getInstance(requireContext());
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("user_info", getContext().MODE_PRIVATE);
        userId = Integer.parseInt(prefs.getString("user_id", "0"));

        // start with one question block
        addQuestionBlock();

        btnAddQuestion.setOnClickListener(x -> addQuestionBlock());
        btnSaveTest   .setOnClickListener(x -> saveTest());
        return v;
    }

    private void addQuestionBlock() {
        View qBlock = getLayoutInflater().inflate(
                R.layout.item_question_block, containerQuestions, false
        );
        LinearLayout containerChoices = qBlock.findViewById(R.id.container_choices);
        Button btnAddChoice = qBlock.findViewById(R.id.btn_add_choice);

        // start with 2 empty choices
        addChoiceRow(containerChoices);
        addChoiceRow(containerChoices);

        btnAddChoice.setOnClickListener(x -> addChoiceRow(containerChoices));
        containerQuestions.addView(qBlock);
    }

    private void addChoiceRow(LinearLayout choicesContainer) {
        View cr = getLayoutInflater().inflate(
                R.layout.item_choice_row, choicesContainer, false
        );
        ImageButton btnRemove = cr.findViewById(R.id.btn_remove_choice);
        btnRemove.setOnClickListener(x -> choicesContainer.removeView(cr));
        choicesContainer.addView(cr);
    }

    private void saveTest() {
        String title = etTitle.getText().toString().trim();
        if (title.isEmpty() || containerQuestions.getChildCount() == 0) {
            // show error…
            return;
        }

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        executor.execute(() -> {
            // 1) insert test header
            UserTest t = new UserTest(userId, title, now);
            long testId = db.userTestDao().insertTest(t);

            // 2) for each question block…
            for (int i = 0; i < containerQuestions.getChildCount(); i++) {
                View qBlock = containerQuestions.getChildAt(i);
                EditText etQ = qBlock.findViewById(R.id.et_question_text);
                String qText = etQ.getText().toString().trim();
                if (qText.isEmpty()) continue;

                long questionId = db.userTestQuestionDao()
                        .insertQuestion(new UserTestQuestion((int)testId, qText));

                // 3) for each choice row…
                LinearLayout choicesContainer = qBlock.findViewById(R.id.container_choices);
                for (int j = 0; j < choicesContainer.getChildCount(); j++) {
                    View cr = choicesContainer.getChildAt(j);
                    EditText etC = cr.findViewById(R.id.et_choice_text);
                    RadioButton rb = cr.findViewById(R.id.rb_correct);
                    String cText = etC.getText().toString().trim();
                    boolean isCorrect = rb.isChecked();
                    if (cText.isEmpty()) continue;

                    db.userTestChoiceDao().insertChoice(
                            new UserTestChoice((int)questionId, cText, isCorrect)
                    );
                }
            }

            // finish activity on UI thread
            requireActivity().runOnUiThread(() -> requireActivity().finish());
        });
    }
}

package com.example.project_quiz_app.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.CreateTestActivity;
import com.example.project_quiz_app.controller.PracticeActivity;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.UserTest;
import com.example.project_quiz_app.view.adapters.TestAdapter;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PracticeFragment extends Fragment implements PracticeActivity.PracticeListener {
    private RecyclerView rvTests;
    private Button btnAddTest;

    private TestAdapter adapter;

    private Executor executor = Executors.newSingleThreadExecutor();
    private AppDatabase db;

    private void bindingView(View v) {
        db = AppDatabase.getInstance(requireContext());
        executor = Executors.newSingleThreadExecutor();

        rvTests    = v.findViewById(R.id.recycler_tests);
        btnAddTest = v.findViewById(R.id.btn_add_test);

        rvTests.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TestAdapter(
                // on item click → start the test
                test -> ((PracticeActivity) requireActivity()).takeTest(test.id),

                // on delete click → call your fragment's deleteTest(...)
                this::deleteTest
        );
        rvTests.setAdapter(adapter);
    }

    private void bindingAction() {
        // load existing tests
        ((PracticeActivity) requireActivity()).loadTests(this);

        btnAddTest.setOnClickListener(x -> {
            // prompt for title
            Intent i = new Intent(requireActivity(), CreateTestActivity.class);
            startActivity(i);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {
        View v = inflater.inflate(R.layout.fragment_practice, c, false);
        bindingView(v);
        bindingAction();
        return v;
    }

    // PracticeListener:
    @Override
    public void onTestsLoaded(List<UserTest> tests) {
        adapter.submitList(tests);
    }
    @Override
    public void onTestCreated() {
        // reload after creation
        ((PracticeActivity) requireActivity()).loadTests(this);
    }

    private void deleteTest(UserTest test) {
        executor.execute(() -> {
            db.userTestDao().deleteTest(test);
            // reload the updated list:
            List<UserTest> updated = db.userTestDao().listTests(
                    Integer.parseInt(
                            requireActivity()
                                    .getSharedPreferences("user_info", MODE_PRIVATE)
                                    .getString("user_id", "0")
                    )
            );
            requireActivity().runOnUiThread(() -> adapter.submitList(updated));
        });
    }
}

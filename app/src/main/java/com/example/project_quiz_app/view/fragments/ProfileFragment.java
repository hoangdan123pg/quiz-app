package com.example.project_quiz_app.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.AppDatabase;

public class ProfileFragment extends Fragment {
    private AppDatabase db;
    private ImageView ivAvatar;
    private EditText etUsername;
    private TextView tvEmail, tvCurrentStreak, tvBestStreak;
    private Button btnChangeAvatar, btnSave;

    private SharedPreferences prefs;
    private Account currentUser;

    private void bindingView(View view) {
        // 1) init Room
        db = AppDatabase.getInstance(requireContext());

        // 2) prefs
        prefs = requireActivity()
                .getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // 3) views
        ivAvatar        = view.findViewById(R.id.iv_avatar);
        etUsername      = view.findViewById(R.id.et_username);
        tvEmail         = view.findViewById(R.id.tv_email);
        tvCurrentStreak = view.findViewById(R.id.tv_current_streak);
        tvBestStreak    = view.findViewById(R.id.tv_best_streak);
        btnChangeAvatar = view.findViewById(R.id.btn_change_avatar);
        btnSave         = view.findViewById(R.id.btn_save);

        // 4) load from prefs & db
        int userId = Integer.parseInt(prefs.getString("user_id", "0"));
        currentUser = db.accountDao().getAccountByEmail(
                prefs.getString("user_email", null)
        );
        if (currentUser != null) {
            etUsername.setText(currentUser.getUserName());
            tvEmail.setText(currentUser.getEmail());
            tvCurrentStreak.setText(
                    String.valueOf(currentUser.getCurrentStreak())
            );
            tvBestStreak.setText(
                    String.valueOf(currentUser.getBestStreak())
            );
            String avatar = prefs.getString("user_avatar", "");
            if (!avatar.isEmpty()) {
                ivAvatar.setImageURI(Uri.parse(avatar));
            }
        }
    }

    private void bindingAction() {
        // (1) change avatar flow, if you want:
        btnChangeAvatar.setOnClickListener(v -> {
            // TODO: launch an imagepicker and update ivAvatar & currentUser.avatarPath
        });

        // (2) save button
        btnSave.setOnClickListener(v -> {
            String newName = etUsername.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(getContext(),
                        "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            // update object + DB
            currentUser.setUserName(newName);
            db.accountDao().updateAccount(currentUser);

            // update SharedPreferences
            prefs.edit()
                    .putString("user_name", currentUser.getUserName())
                    .putString("user_avatar", currentUser.getAvatarPath())
                    .putInt("user_current_streak", currentUser.getCurrentStreak())
                    .putInt("user_best_streak", currentUser.getBestStreak())
                    .putString("user_last_study", currentUser.getLastStudyDate())
                    .putString("user_updated", currentUser.getUpdatedDate())
                    .apply();

            Toast.makeText(getContext(),
                    "Profile updated successfully", Toast.LENGTH_SHORT).show();
        });
    }

    public ProfileFragment() { /* required empty constructor */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_profile, container, false
        );
        bindingView(view);
        bindingAction();
        return view;
    }
}

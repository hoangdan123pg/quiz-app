package com.example.project_quiz_app.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.AppDatabase;

public class ProfileFragment extends Fragment {
    private ImageView ivAvatar;
    private EditText etUsername;
    private TextView tvEmail, tvCurrentStreak, tvBestStreak;
    private Button btnSave;
    private Account currentUser;
    private AppDatabase db;
    private SharedPreferences prefs;

    /**
     * 1) Register an ActivityResultLauncher for SAF pick.
     *    The lambda here is the *callback* that runs when the user finishes picking.
     */
    private final ActivityResultLauncher<Intent> pickPic =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {
                            Uri uri = result.getData().getData();

                            // IMPORTANT: persist read permission
                            requireContext().getContentResolver()
                                    .takePersistableUriPermission(
                                            uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            ivAvatar.setImageURI(uri);
                            currentUser.setAvatarPath(uri.toString());
                        }
                    }
            );


    private void bindingView(View v) {
        db    = AppDatabase.getInstance(requireContext());
        prefs = requireActivity()
                .getSharedPreferences("user_info", Context.MODE_PRIVATE);

        ivAvatar        = v.findViewById(R.id.iv_avatar);
        etUsername      = v.findViewById(R.id.et_username);
        tvEmail         = v.findViewById(R.id.tv_email);
        tvCurrentStreak = v.findViewById(R.id.tvCurrentStreak);
        tvBestStreak    = v.findViewById(R.id.tvBestStreak);
        btnSave         = v.findViewById(R.id.btn_save);

        // Load the current user
        String email = prefs.getString("user_email", "");
        currentUser = db.accountDao().getAccountByEmail(email);
        if (currentUser != null) {
            tvEmail.setText(currentUser.getEmail());
            etUsername.setText(currentUser.getUserName());
            tvCurrentStreak.setText(
                    "Current Streak: " + currentUser.getCurrentStreak() + " days");
            tvBestStreak.setText(
                    "Best Streak: " + currentUser.getBestStreak() + " days");

            String av = currentUser.getAvatarPath();
            if (!TextUtils.isEmpty(av)) {
                ivAvatar.setImageURI(Uri.parse(av));
            }
        }
    }

    private void bindingAction() {
        // When avatar tapped, launch the SAF document picker
        ivAvatar.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            pick.addCategory(Intent.CATEGORY_OPENABLE);
            pick.setType("image/*");
            // these flags let you keep URI access across restarts
            pick.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            );
            pickPic.launch(pick);
        });

        // Save updated name & avatar URI to DB & prefs
        btnSave.setOnClickListener(v -> {
            String newName = etUsername.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(getContext(),
                        "Name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            currentUser.setUserName(newName);

            // Persist on background thread
            new Thread(() -> {
                db.accountDao().updateAccount(currentUser);
                prefs.edit()
                        .putString("user_name", newName)
                        .putString("user_avatar", currentUser.getAvatarPath())
                        .apply();
            }).start();

            Toast.makeText(getContext(),
                    "Profile saved", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {
        View v = inflater.inflate(R.layout.fragment_profile, c, false);
        bindingView(v);
        bindingAction();
        return v;
    }
}

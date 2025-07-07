package com.example.project_quiz_app.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.controller.MainActivity;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.AppDatabase;

public class LoginFragment extends Fragment {
    private AppDatabase db;
    private TextView btnRegister;
    private EditText email_lg, password_lg;
    private Button btnLogin;

    private void bindingView(View view) {
        // Khởi tạo Room Database
        db = AppDatabase.getInstance(requireContext());
        // Lưu vào biến instance
        btnRegister = view.findViewById(R.id.text_register);
        email_lg = view.findViewById(R.id.email_lg);
        password_lg = view.findViewById(R.id.password_lg);
        btnLogin = view.findViewById(R.id.btn_login);
    }
    private void bindingAction() {
        btnRegister.setOnClickListener(this::btnRegisterClick);
        btnLogin.setOnClickListener(this::btnLoginClick);
    }
    private void btnLoginClick(View view){
        String emailInput = email_lg.getText().toString().trim();
        String passwordInput = password_lg.getText().toString().trim();
        // validateif
        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        // check account
        Account existingAccount = db.accountDao().checkLogin(emailInput, passwordInput);
        if (existingAccount != null) {
            Toast.makeText(getContext(), "Login successfully!", Toast.LENGTH_SHORT).show();

            // chuyen sang activity main
            // Delay 1s rồi chuyển activity
            view.postDelayed(() -> {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }, 1000);


        } else {
            Toast.makeText(getContext(), "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }
    private void btnRegisterClick(View view) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView2, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    public LoginFragment() {
        // Constructor rỗng bắt buộc
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Gắn layout cho fragment
        // Nạp layout cho fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        bindingView(view);
        bindingAction();
        return view;
    }
}

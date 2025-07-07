package com.example.project_quiz_app.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.Account;
import com.example.project_quiz_app.model.AppDatabase;

public class RegisterFragment extends Fragment {
    private AppDatabase db;
    private EditText email, password, confirmPassword, name;
    private Button btnRegister;
    private TextView btnBack_login_fragment;

    private void bindingView(View view) {
        // Khởi tạo Room Database
        db = AppDatabase.getInstance(requireContext());
        // Ánh xạ các view
        email = view.findViewById(R.id.email_register);
        password = view.findViewById(R.id.password_register);
        confirmPassword = view.findViewById(R.id.confirm_password_register);
        name = view.findViewById(R.id.name_register);
        btnRegister = view.findViewById(R.id.btn_register);
        btnBack_login_fragment = view.findViewById(R.id.back_login_fragment);
    }

    private void bindingAction() {
        btnRegister.setOnClickListener(this::btnRegisterClick);
        btnBack_login_fragment.setOnClickListener(this::btnBackLoginClick);
    }
    public RegisterFragment() {
        // Bắt buộc phải có constructor rỗng
    }
    private void btnRegisterClick(View view) {
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String confirmPasswordInput = confirmPassword.getText().toString().trim();
        String nameInput = name.getText().toString().trim();
        // Validate đơn giản
        if (emailInput.isEmpty() || passwordInput.isEmpty() || nameInput.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordInput.equals(confirmPasswordInput)) {
            Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check email đã tồn tại chưa
        Account existingAccount = db.accountDao().getAccountByEmail(emailInput);
        if (existingAccount != null) {
            Toast.makeText(getContext(), "Email already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo Account mới
        Account account = new Account(nameInput, emailInput, passwordInput);
        long id = db.accountDao().insertUser(account);
        if (id > 0) {
            Toast.makeText(getContext(), "Account registered successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error when registering account!", Toast.LENGTH_SHORT).show();
        }
    }
        private void btnBackLoginClick(View view) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView2, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        }

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

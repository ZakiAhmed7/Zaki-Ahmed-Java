package com.example.zakiahmedjava.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zakiahmedjava.MainActivity;
import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    protected ActivitySignupBinding binding;
    protected String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    protected ProgressDialog progressDialog;

    protected FirebaseAuth mAuth;
    protected FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.signupButton.setOnClickListener(v -> {
            performAuthentication();
        });

        binding.textViewAlreadyHaveAnAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

    }

    private void performAuthentication() {
        String userName = binding.editTextPersonName.getText().toString();
        String email = binding.editTextSignupEmail.getText().toString();
        String password = binding.editTextSignupPassword.getText().toString();
        String confirmPassword = binding.editTextConfirmPassword.getText().toString();

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (userName.isEmpty() || userName.length() < 4)
            binding.editTextPersonName.setError("Enter username above 4 characters");
        else if(!email.matches(emailPattern))
            binding.editTextSignupEmail.setError("Enter Valid email address");
        else if (password.isEmpty())
            binding.editTextSignupPassword.setError("Password empty. Enter password");
        else if (password.length() < 8)
            binding.editTextSignupPassword.setError("Password should contain atleast 8 characters");
        else if (!password.matches(confirmPassword))
            binding.editTextConfirmPassword.setError("Password does not match");
        else {
            progressDialog.setTitle("Registering");
            progressDialog.setMessage("Please wait... Registering");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        directLogin();
                        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "Error:: " +task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void directLogin() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
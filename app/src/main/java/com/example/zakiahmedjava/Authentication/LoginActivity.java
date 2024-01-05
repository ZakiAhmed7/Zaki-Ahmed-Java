package com.example.zakiahmedjava.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.zakiahmedjava.MainActivity;
import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    protected ActivityLoginBinding binding;
    protected ProgressDialog progressDialog;
    protected String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;
    protected GoogleSignInOptions googleSignInOptions;
    protected GoogleSignInClient googleSignInClient;
    protected CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FacebookSdk.sdkInitialize(getApplicationContext());

        binding.buttonLogin.setOnClickListener(v -> {
            performAuthentication();
        });

        GoogleSignInAccount account = GoogleSignIn
                .getLastSignedInAccount(getApplicationContext());

        if (account != null) {
            directLogin();
        }

        binding.btnGoogle.setOnClickListener(v -> {
            googleSignInOptions = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn
                    .getClient(getApplicationContext(), googleSignInOptions);

            Intent googleSignInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(googleSignInIntent, 101);
        });

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(@NonNull FacebookException e) {
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        binding.btnFacebook.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
        });

        binding.textViewSignup.setOnClickListener(v -> {
//            startActivity(new Intent(LoginFragment.this, SignupActivity.class));
        });

    }

    private void directLogin() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void performAuthentication() {
        String loginEmail = binding.editTextLoginEmail.getText().toString();
        String loginPassword = binding.editTextLoginPassword.getText().toString();

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (!loginEmail.matches(emailPattern)) {
            binding.editTextLoginEmail.setError("Enter valid email address");
            binding.editTextLoginEmail.requestFocus();
        } else if (loginPassword.isEmpty()) {
            binding.editTextLoginPassword.setError("Enter password");
            if (loginPassword.length() < 8)
                binding.editTextLoginPassword.setError("Password should contain 8 or more characters");
        } else {
            progressDialog.setTitle("Logging in");
            progressDialog.setMessage("Please wait, we are logging you in...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(loginEmail ,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        directLogin();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed, try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something when wrong!", Toast.LENGTH_SHORT).show();
            }
        }
        // Facebook login
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
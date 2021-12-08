package com.ppb.lifenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText etMail, etUsername, etPassword, etPassword2, etNamaLengkap, etNoHp, etTglLahir;
    Button btnRegister;
    String username, password, password2, nama, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        TextView toLogin = findViewById(R.id.btn_to_login);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();

        etMail = findViewById(R.id.et_mail);
        etNamaLengkap = findViewById(R.id.et_nama_lengkap);
        etNoHp = findViewById(R.id.et_no_hp);
        etTglLahir = findViewById(R.id.et_birth_date);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPassword2 = findViewById(R.id.et_password2);
        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.btn_register);

//        if (mAuth.getCurrentUser() != null){
//            startActivity(new Intent(RegisterPage.this, LoginPage.class));
//            finish();
//        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNamaLengkap.getText().toString().trim();
                mail = etMail.getText().toString().trim();
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                password2 = etPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(nama)){
                    etNamaLengkap.setError("Nama wajib diisi");
                    return;
                }
                else if (TextUtils.isEmpty(mail)){
                    etMail.setError("Email wajib diisi");
                    return;
                }
                else if (TextUtils.isEmpty(username)){
                    etUsername.setError("Username wajib diisi");
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    etPassword.setError("Password wajib diisi");
                    return;
                }
                else if (password.length() < 8){
                    etPassword.setError("Panjang password minimal 8 karakter");
                }
                else if (!password.equals(password2)){
                    etPassword2.setError("Password tidak sama");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    registrasi();
                }
            }
        });
    }

    private void registrasi() {
        mail = etMail.getText().toString();
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterPage.this, "Registration Succesfull", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterPage.this, "Registration Failed, " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
package com.ppb.lifenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    public class User{
        private String etMail;
        private String etNamaLengkap;
        private String etTglLahir;
        private String etNoHp;
        private String etPassword;

        public User(){
        }

        public String getEtMail() {
            return etMail;
        }

        public String getEtNamaLengkap() {
            return etNamaLengkap;
        }

        public String getEtTglLahir() {
            return etTglLahir;
        }

        public String getEtNoHp() {
            return etNoHp;
        }

        public String getEtPassword() {
            return etPassword;
        }

        public void setEtMail(String etMail) {
            this.etMail = etMail;
        }

        public void setEtNamaLengkap(String etNamaLengkap) {
            this.etNamaLengkap = etNamaLengkap;
        }

        public void setEtNoHp(String etNoHp) {
            this.etNoHp = etNoHp;
        }

        public void setEtTglLahir(String etTglLahir) {
            this.etTglLahir = etTglLahir;
        }

        public void setEtPassword(String etPassword) {
            this.etPassword = etPassword;
        }
    }

    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText etMail, etPassword, etPassword2, etNamaLengkap, etNoHp, etTglLahir;
    Button btnRegister;
    String password, password2, nama, mail ,tgllahir, nohp ;
    DatabaseReference dbRef;
    User user;

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
        etPassword = findViewById(R.id.et_password);
        etPassword2 = findViewById(R.id.et_password2);
        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.btn_register);

        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "data");
                datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String tahun = ""+datePicker.getYear();
                        String bulan = ""+(datePicker.getMonth()+1);
                        String hari = ""+datePicker.getDayOfMonth();
                        String tgl = hari+"-"+bulan+"-"+tahun;
                        etTglLahir.setText(tgl);
                    }
                });
            }
        });
        user = new User();

        dbRef = FirebaseDatabase.getInstance().getReference();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNamaLengkap.getText().toString().trim();
                mail = etMail.getText().toString().trim();
                tgllahir = etTglLahir.getText().toString().trim();
                nohp = etNoHp.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                password2 = etPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(nama)){
                    etNamaLengkap.setError("Nama wajib diisi");
                    etNamaLengkap.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(mail)){
                    etMail.setError("Email wajib diisi");
                    etMail.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(password)){
                    etPassword.setError("Password wajib diisi");
                    etPassword.requestFocus();
                    return;
                }
                else if (password.length() < 8){
                    etPassword.setError("Panjang password minimal 8 karakter");
                    etPassword.requestFocus();
                }
                else if (!password.equals(password2)){
                    etPassword2.setError("Password tidak sama");
                    etPassword2.requestFocus();
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
        password = etPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user.setEtMail(mail);
                            user.setEtNamaLengkap(nama);
                            user.setEtTglLahir(tgllahir);
                            user.setEtNoHp(nohp);
                            user.setEtPassword(password);

                            dbRef = FirebaseDatabase.getInstance().getReference().child(mail.substring(0, mail.indexOf("@")));
                            dbRef.setValue(user);

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
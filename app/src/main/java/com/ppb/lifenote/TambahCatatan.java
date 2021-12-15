package com.ppb.lifenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahCatatan extends AppCompatActivity {

//    public static final String TAG = "TambahCatatan";

    private EditText user, etTglCatatan, barang, pengeluaran, pemasukan, keterangan;
    private Button btnTambahCatatan;
    private TextView btnKeDashboard;
    private FirebaseAuth auth;
    int hitungBarang = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

        etTglCatatan = findViewById(R.id.et_tgl_catatan);
        barang = findViewById(R.id.et_barang);
        pengeluaran = findViewById(R.id.et_pengeluaran);
        pemasukan = findViewById(R.id.et_pemasukan);
        keterangan = findViewById(R.id.et_keterangan);

        btnKeDashboard = findViewById(R.id.btn_kembali_ke_dashboard);
        btnKeDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TambahCatatan.this, DashboardPage.class));
            }
        });

        barang.clearFocus();
        pengeluaran.clearFocus();
        pemasukan.clearFocus();
        keterangan.clearFocus();

        etTglCatatan = findViewById(R.id.et_tgl_catatan);
        etTglCatatan.setOnClickListener(new View.OnClickListener() {
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
                        etTglCatatan.setText(tgl);
                    }
                });
            }
        });

        user = findViewById(R.id.et_user);
        btnTambahCatatan = findViewById(R.id.btn_submit_form_catatan);

        btnTambahCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(TambahCatatan.this, "text", Toast.LENGTH_LONG).show();
//                DialogCatatan dialogCatatan = new DialogCatatan();
//                dialogCatatan.show(getSupportFragmentManager(),"form");

                String Stgl = etTglCatatan.getText().toString();
                String Sbarang = barang.getText().toString();
                String Spengeluaran = pengeluaran.getText().toString();
                String Spemasukan = pemasukan.getText().toString();
                String Sketerangan = keterangan.getText().toString();

                if (TextUtils.isEmpty(Stgl)){
                    etTglCatatan.setError("Tanggal Harus Diisi");
                    etTglCatatan.requestFocus();
                }
                else if (TextUtils.isEmpty(Sbarang)){
                    barang.setError("Nama Barang Harus Diisi");
                    barang.requestFocus();
                }
                else if (TextUtils.isEmpty(Spengeluaran)){
                    pengeluaran.setError("Pengeluaran Harus Diisi");
                    pengeluaran.requestFocus();
                }
                else if (TextUtils.isEmpty(Spemasukan)){
                    pemasukan.setError("Pemasukan Harus Diisi");
                    pemasukan.requestFocus();
                }
                else if (TextUtils.isEmpty(Sketerangan)){
                    keterangan.setError("keterangan Harus Diisi");
                    keterangan.requestFocus();
                }
                else{
                    //Mendapatkan UserID dari pengguna yang Terautentikasi
                    auth = FirebaseAuth.getInstance();
                    String getUserID = auth.getCurrentUser().getEmail();
                    //Mendapatkan Instance dari Database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference getReference;

                    getReference = database.getReference(); // Mendapatkan Referensi dari Database

                    //(String tanggal, String namabarang, String pengeluaran, String pemasukan, String keterangan
                    getReference.child(getUserID.substring(0, getUserID.indexOf("@"))).child("barang").push()
                            .setValue(new data_catatan(Stgl, Sbarang, Spengeluaran, Spemasukan, Sketerangan))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    etTglCatatan.setText("");
                                    barang.setText("");
                                    pengeluaran.setText("");
                                    pemasukan.setText("");
                                    keterangan.setText("");
                                    Toast.makeText(TambahCatatan.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            etTglCatatan.setText(Stgl);
                            barang.setText(Sbarang);
                            pengeluaran.setText(Spengeluaran);
                            pemasukan.setText(Spemasukan);
                            keterangan.setText(Sketerangan);
                            Toast.makeText(TambahCatatan.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                    hitungBarang++;
                }
            }
        });

    }
    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(TambahCatatan.this, ProfilePage.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TambahCatatan.this, LoginPage.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
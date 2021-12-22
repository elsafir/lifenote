package com.ppb.lifenote.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ppb.lifenote.DatePickerFragment;
import com.ppb.lifenote.R;
import com.ppb.lifenote.dataclass.data_catatan;

public class TambahCatatan extends AppCompatActivity {

    private EditText nominal, etTglCatatan, barang, pengeluaran, pemasukan, keterangan;
    private Button btnTambahCatatan;
    private TextView btnKeDashboard;
    private FirebaseAuth auth;
    String jenisTransaksi = null;
    RadioGroup radioGroup;
    String Spengeluaran = "", Spemasukan = "";

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_pengeluaran:
                jenisTransaksi = "pengeluaran";
                break;
            case R.id.rb_pemasukan:
                jenisTransaksi = "pemasukan";
                break;
        }
//        Toast.makeText(TambahCatatan.this, "Jenis transaksi: "+jenisTransaksi, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

        etTglCatatan = findViewById(R.id.et_tgl_catatan);
        barang = findViewById(R.id.et_barang);
        keterangan = findViewById(R.id.et_keterangan);
        radioGroup = findViewById(R.id.jenis_transaksi);
        nominal = findViewById(R.id.et_nominal);

        btnKeDashboard = findViewById(R.id.btn_kembali_ke_dashboard);
        btnKeDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TambahCatatan.this, DashboardPage.class));
            }
        });

        barang.clearFocus();
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

//        user = findViewById(R.id.et_user);
        btnTambahCatatan = findViewById(R.id.btn_submit_form_catatan);

        btnTambahCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Stgl = etTglCatatan.getText().toString();
                String Sbarang = barang.getText().toString();
                String Sketerangan = keterangan.getText().toString();
                String Snominal = nominal.getText().toString();

                if (TextUtils.isEmpty(Stgl)){
                    etTglCatatan.setError("Tanggal Harus Diisi");
                    etTglCatatan.requestFocus();
                }
                else if (TextUtils.isEmpty(Sbarang)){
                    barang.setError("Nama Barang Harus Diisi");
                    barang.requestFocus();
                }
                else if (jenisTransaksi == null){
                    Toast.makeText(TambahCatatan.this, "Jenis transaksi harus diisi", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(Sketerangan)){
                    keterangan.setError("keterangan Harus Diisi");
                    keterangan.requestFocus();
                }
                else if (TextUtils.isEmpty(Snominal)){
                    nominal.setError("Nominal "+jenisTransaksi+" harus diisi.");
                    nominal.requestFocus();
                }
                else{
                    // mendapatkan nominal dari inputan user
                    if (jenisTransaksi == "pemasukan"){
                        Spemasukan = nominal.getText().toString();
                        Spengeluaran = "0";
                    }
                    else{
                        Spengeluaran = nominal.getText().toString();
                        Spemasukan = "0";
                    }
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
                                    nominal.setText("");
                                    keterangan.setText("");
                                    Toast.makeText(TambahCatatan.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            etTglCatatan.setText(Stgl);
                            barang.setText(Sbarang);
                            nominal.setText(Snominal);
                            keterangan.setText(Sketerangan);
                            Toast.makeText(TambahCatatan.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
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
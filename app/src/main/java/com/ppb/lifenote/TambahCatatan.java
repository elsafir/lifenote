package com.ppb.lifenote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class TambahCatatan extends AppCompatActivity {

//    public static final String TAG = "TambahCatatan";

    private EditText user, etTglCatatan, barang, pengeluaran, pemasukan, keterangan;
    private Button btnTambahCatatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

//        String getUser = getIntent().getStringExtra("EXTRA_USER");
//        String getTgl = getIntent().getStringExtra("EXTRA_TGL");

        barang = findViewById(R.id.barang);
        pengeluaran = findViewById(R.id.pengeluaran);
        pemasukan = findViewById(R.id.pemasukan);
        keterangan = findViewById(R.id.keterangan);

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
//                Toast.makeText(DashboardPage.this, tgl, Toast.LENGTH_LONG).show();
                        //Intent ke_catatan = new Intent(DashboardPage.this, TambahCatatan.class);
                        //ke_catatan.putExtra("EXTRA_USER", "Dico");
                        //ke_catatan.putExtra("EXTRA_TGL", tgl);
                        //startActivity(ke_catatan);
//                        startActivity(new Intent(DashboardPage.this, TambahCatatan.class));
                    }
                });
//                Toast.makeText(DashboardPage.this, tgl, Toast.LENGTH_LONG).show();
//                Intent ke_catatan = new Intent(DashboardPage.this, TambahCatatan.class);
//                ke_catatan.putExtra("EXTRA_USER", "Dico");
//                ke_catatan.putExtra("EXTRA_TGL", hasilTgl);
//                startActivity(new Intent(DashboardPage.this, TambahCatatan.class));
            }
        });

        user = findViewById(R.id.et_user);
        btnTambahCatatan = findViewById(R.id.btn_submit_form_catatan);

//        user.setText(getUser);
//        tglTransaksi.setText(getTgl);

        btnTambahCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tempuser = findViewById(R.id.et_user);
//                temptglTransaksi = findViewById(R.id.et_tgl_catatan);

//                String texx = getUser+" & "+getTgl;
                Toast.makeText(TambahCatatan.this, "text", Toast.LENGTH_LONG).show();
            }
        });

    }
    private View.OnTouchListener otl = new View.OnTouchListener() {
        public boolean onTouch (View v, MotionEvent event) {
            return true; // the listener has consumed the event
        }
    };
}
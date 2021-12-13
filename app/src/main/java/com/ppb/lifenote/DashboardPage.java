package com.ppb.lifenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardPage extends AppCompatActivity {

    private FloatingActionButton btnDatePicker;

    String hasilTgl, tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);

        btnDatePicker = findViewById(R.id.fb_tambah_data);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardPage.this, TambahCatatan.class));
            }
        });

    }

    public String TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                tgl = hari+"-"+bulan+"-"+tahun;
//                Toast.makeText(DashboardPage.this, tgl, Toast.LENGTH_LONG).show();
                //Intent ke_catatan = new Intent(DashboardPage.this, TambahCatatan.class);
                //ke_catatan.putExtra("EXTRA_USER", "Dico");
                //ke_catatan.putExtra("EXTRA_TGL", tgl);
                //startActivity(ke_catatan);
                //startActivity(new Intent(DashboardPage.this, TambahCatatan.class));
            }
        });
        return tgl;
    }

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
                startActivity(new Intent(DashboardPage.this, ProfilePage.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardPage.this, LoginPage.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
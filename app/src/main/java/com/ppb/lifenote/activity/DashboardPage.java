package com.ppb.lifenote.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ppb.lifenote.R;
import com.ppb.lifenote.adapter.DashboardAdapter;
import com.ppb.lifenote.dataclass.Barang;

import java.util.ArrayList;

public class DashboardPage extends AppCompatActivity {

    private FloatingActionButton btnDatePicker;

    //Deklarasi Variable untuk RecyclerView
    RecyclerView recyclerView;

    private FirebaseAuth auth;

    DatabaseReference database;
    DashboardAdapter dashboardAdapter;
    ArrayList<Barang> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);

        auth = FirebaseAuth.getInstance();
        String getUserID = auth.getCurrentUser().getEmail();
        recyclerView = findViewById(R.id.rv_item_data);
        database = FirebaseDatabase.getInstance().getReference().child(getUserID.substring(0, getUserID.indexOf("@"))).child("barang");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        dashboardAdapter = new DashboardAdapter(this,list);
        recyclerView.setAdapter(dashboardAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Barang barang = dataSnapshot.getValue(Barang.class);
                    list.add(barang);

                }
                dashboardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDatePicker = findViewById(R.id.fb_tambah_data);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardPage.this, TambahCatatan.class));
            }
        });

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
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ppb.lifenote.R;
import com.ppb.lifenote.adapter.ProfileAdapter;
import com.ppb.lifenote.dataclass.UserClass;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity {

    //Deklarasi Variable untuk RecyclerView
    RecyclerView recyclerView;

    private FirebaseAuth auth;

    DatabaseReference database;
    ProfileAdapter profileAdapter;
    ArrayList<UserClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        auth = FirebaseAuth.getInstance();
        String getUserID = auth.getCurrentUser().getEmail();
        recyclerView = findViewById(R.id.rv_data_profile);
        database = FirebaseDatabase.getInstance().getReference().child(getUserID.substring(0, getUserID.indexOf("@"))).child("profile");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        profileAdapter = new ProfileAdapter(this,list);
        recyclerView.setAdapter(profileAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    UserClass user = dataSnapshot.getValue(UserClass.class);
                    list.add(user);

                }
                profileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                startActivity(new Intent(ProfilePage.this, ProfilePage.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfilePage.this, LoginPage.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
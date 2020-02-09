package com.leventenyiro.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity {

    private EditText inputName;
    private Button btnSearch;
    private TextView textSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        select();

        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                select();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void select() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Member").orderByChild("name");
        if (!inputName.getText().toString().isEmpty()) {
            String search = (inputName.getText().toString().substring(0,1).toUpperCase() + inputName.getText().toString().substring(1).toLowerCase());
            Toast.makeText(this, search, Toast.LENGTH_SHORT).show();
            query = query.startAt(search).endAt(search + "\uf8ff");
        }
        query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    textSearch.setText("");
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            textSearch.append(snapshot.child("name").getValue().toString() + "\n");
                            textSearch.append(snapshot.child("email").getValue().toString() + "\n\n");
                        }
                    } else {
                        textSearch.setText("Nincs ilyen adat!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    private void init() {
        inputName = findViewById(R.id.inputName);
        btnSearch = findViewById(R.id.btnSearch);
        textSearch = findViewById(R.id.textSearch);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SearchActivity.this, MainActivity.class));
        finish();
    }
}

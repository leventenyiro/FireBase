package com.leventenyiro.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputName.getText().toString().isEmpty())
                {
                    Query query = FirebaseDatabase.getInstance().getReference("Member").orderByChild("name").startAt("\uf8ff" + inputName.getText().toString()).endAt("\uf8ff");//equalTo("\uf8ff" + inputName.getText().toString() + "\uf8ff");
                    query.addListenerForSingleValueEvent(valueEventListener);
                    /*ref = FirebaseDatabase.getInstance().getReference().child("Member").;
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            textMember.append(dataSnapshot.child("name").getValue().toString() + "\n");
                            textMember.append(dataSnapshot.child("email").getValue().toString() + "\n\n");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                }
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            textSearch.setText("");
            if (dataSnapshot.exists())
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    textSearch.append(snapshot.child("name").getValue().toString() + "\n");
                    textSearch.append(snapshot.child("email").getValue().toString() + "\n\n");
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void init() {
        inputName = findViewById(R.id.inputName);
        btnSearch = findViewById(R.id.btnSearch);
        textSearch = findViewById(R.id.textSearch);
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
    }

}

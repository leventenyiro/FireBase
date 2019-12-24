package com.leventenyiro.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputEmail;
    private Button btnInsert, btnSearch;
    private DatabaseReference ref;
    private Member member;
    private long id, count;
    private TextView textMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected())
                {
                    if (!inputName.getText().toString().isEmpty() && !inputEmail.getText().toString().isEmpty())
                    {
                        member = new Member(inputName.getText().toString(), inputEmail.getText().toString());
                        //ref.push().setValue(member);
                        //ref.child("member1").setValue(member);
                        Toast.makeText(MainActivity.this, "Sikeres rögzítés", Toast.LENGTH_SHORT).show();
                        inputName.setText("");
                        inputEmail.setText("");
                        ref.child(String.valueOf(id + 1)).setValue(member);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Valami nincs kitöltve!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Nincs kapcsolódva az internethez!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // listázás
        if (isNetworkConnected())
        {
            ref.addListenerForSingleValueEvent(valueEventListener);

            /*ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        id = dataSnapshot.getChildrenCount();
                        for (int i = 1; i < id + 1; i++)
                        {
                            ref = FirebaseDatabase.getInstance().getReference().child("Member").child(String.valueOf(i));
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    textMember.append(dataSnapshot.child("name").getValue().toString() + "\n");
                                    textMember.append(dataSnapshot.child("email").getValue().toString() + "\n\n");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

            // ha egy elemet listázunk ki
            /*ref = FirebaseDatabase.getInstance().getReference().child("Member").child("1");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    textMember.setText(dataSnapshot.child("name").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/
        }
        else
        {
            textMember.setText("Nincs intenetkapcsolat!");
        }
    }

    private void init() {
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        btnInsert = findViewById(R.id.btnInsert);
        btnSearch = findViewById(R.id.btnSearch);
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
        textMember = findViewById(R.id.textMember);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    id = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists())
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    textMember.append(snapshot.child("name").getValue().toString() + "\n");
                    textMember.append(snapshot.child("email").getValue().toString() + "\n\n");
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

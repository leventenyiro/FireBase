package com.leventenyiro.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputEmail;
    private Button btnInsert;
    private DatabaseReference ref;
    private Member member;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "Firebase connection success!", Toast.LENGTH_SHORT).show();

        init();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    id = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected())
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
                    Toast.makeText(MainActivity.this, "Nincs kapcsolódva az internethez!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        btnInsert = findViewById(R.id.btnInsert);
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

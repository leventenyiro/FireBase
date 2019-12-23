package com.leventenyiro.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText inputName, inputEmail;
    private Button btnInsert;
    private DatabaseReference ref;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "Firebase connection success!", Toast.LENGTH_SHORT).show();

        init();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setName(inputName.getText().toString());
                member.setEmail(inputEmail.getText().toString());
                ref.push().setValue(member);
                Toast.makeText(MainActivity.this, "Sikeres rögzítés", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        btnInsert = findViewById(R.id.btnInsert);
        member = new Member();
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
    }
}

package com.example.mybhccnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText bhccID, name, email;
    Button signup, login;
    DatabaseHelper dbHelper;
    public static final String EXTRA_STUDENTID = "studentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bhccID = findViewById(R.id.bhccID);
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.schoolemail);

        signup = findViewById(R.id.button_signup);
        login = findViewById(R.id.button_login);


        dbHelper = new DatabaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bhccId = bhccID.getText().toString().trim();
                String studentName = name.getText().toString();
                String studentEmail = email.getText().toString();

                long result = dbHelper.insertUser(bhccId, studentName, studentEmail);
                if (result != -1) {
                    // Successful insertion
                    Toast.makeText(MainActivity.this, "Student signed up successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    bhccID.setText("");
                    name.setText("");
                    email.setText("");
                } else {
                    // Error occurred
                    Toast.makeText(MainActivity.this, "Failed to sign up user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for loginButton
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bhccId = bhccID.getText().toString().trim();
                String studentName = name.getText().toString();
                String studentEmail = email.getText().toString();

                // Check if the user exists
                boolean loginSuccessful = dbHelper.checkUser(bhccId);

                if (loginSuccessful) {
                    // Login successful, switch to Academic activity
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    intent.putExtra(EXTRA_STUDENTID, bhccId);
                    startActivity(intent);
                    finish(); // Finish current activity to prevent back navigation
                } else {
                    // Login failed
                    Toast.makeText(MainActivity.this, "Invalid student ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
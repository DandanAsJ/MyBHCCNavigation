package com.example.mybhccnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ListView favoriteOfficesListView;
    DatabaseHelper dbHelper;
    ArrayAdapter<String> adapter;
    String studentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentid = getIntent().getStringExtra(MainActivity.EXTRA_STUDENTID);
        Log.e("student", studentid);
        TextView welcome = findViewById(R.id.main_welcome);
        welcome.setText("Welcome Student " + studentid);
        dbHelper = new DatabaseHelper(this);
        favoriteOfficesListView = findViewById(R.id.list_favorite);
        // Populate ListView with favorite offices
        populateFavoriteOfficesListView(studentid);


        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(Login.this, AcademicActivity.class);
                    intent.putExtra(MainActivity.EXTRA_STUDENTID, studentid);
                    startActivity(intent);
                }
                else if(position == 1){
                    Intent intent = new Intent(Login.this, StudentSupport.class);
                    intent.putExtra(MainActivity.EXTRA_STUDENTID, studentid);
                    startActivity(intent);
                }

            }
        };

        ListView listView = (ListView) findViewById(R.id.list_option);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void populateFavoriteOfficesListView(String sid) {
        // Retrieve favorite offices from the database
        ArrayList<String> favoriteOffices = dbHelper.getFavoriteOffices(sid);

        // Create ArrayAdapter to populate ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteOffices);

        // Set adapter to ListView
        favoriteOfficesListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data here
        populateFavoriteOfficesListView(studentid);
    }
}
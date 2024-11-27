package com.example.mybhccnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AcademicActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic);

        String studentid = getIntent().getStringExtra(MainActivity.EXTRA_STUDENTID);

        ListView listAcademics = findViewById(R.id.list_academic);
        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try{
            db = databaseHelper.getReadableDatabase();
            cursor = db.query("ACADEMICS", new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},//map the name to the text in the listview
                    0);
            listAcademics.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(AcademicActivity.this, AcademicOffices.class);
                        intent.putExtra(AcademicOffices.EXTRA_LISTID, (int)id);
                        intent.putExtra(MainActivity.EXTRA_STUDENTID, studentid);
                        startActivity(intent);
                    }
                };

        listAcademics.setOnItemClickListener(itemClickListener);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
package com.example.mybhccnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class SupportOffices extends AppCompatActivity {
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_offices);

        int supportId = (Integer)getIntent().getExtras().get(AcademicOffices.EXTRA_LISTID);
        String studentid = getIntent().getStringExtra(MainActivity.EXTRA_STUDENTID);

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try{
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("SUPPORTS", new String[]{"NAME", "LOCATION", "OPENHOUR"},
                    "_id = ?",
                    new String[]{Integer.toString(supportId)},
                    null, null, null);
            if(cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String locationText = cursor.getString(1);
                String hourText = cursor.getString(2);

                name = findViewById(R.id.name);
                name.setText(nameText);

                TextView location = findViewById(R.id.location);
                location.setText(locationText);

                TextView hour = findViewById(R.id.hour);
                hour.setText(hourText);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        CheckBox academicBox = findViewById(R.id.checkBox2);
        String officeName = name.getText().toString();
        academicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Save the office name and username to the Record table
                    ((DatabaseHelper) databaseHelper).insertRecords(studentid, officeName);
                    Toast.makeText(SupportOffices.this, "Office saved as favorite", Toast.LENGTH_SHORT).show();
                } else {
                    // Remove the office name from the Record table if unchecked (optional)
                    ((DatabaseHelper) databaseHelper).deleteRecords(studentid, officeName);
                    Toast.makeText(SupportOffices.this, "Office removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
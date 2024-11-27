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

public class AcademicOffices extends AppCompatActivity {
    public static final String EXTRA_LISTID = "listId";
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_offices);

        int academicId = (Integer)getIntent().getExtras().get(EXTRA_LISTID);
        String studentid = getIntent().getStringExtra(MainActivity.EXTRA_STUDENTID);

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try{
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("ACADEMICS", new String[]{"NAME", "LOCATION", "OPENHOUR"},
                    "_id = ?",
                    new String[]{Integer.toString(academicId)},
                    null, null, null);
            if(cursor.moveToFirst()){
                String nameText = cursor.getString(0);
                String locationText = cursor.getString(1);
                String hourText = cursor.getString(2);

                name = findViewById(R.id.textView_name);
                name.setText(nameText);

                TextView location = findViewById(R.id.textView_location);
                location.setText(locationText);

                TextView hour = findViewById(R.id.textView_hour);
                hour.setText(hourText);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        CheckBox academicBox = findViewById(R.id.checkBox_aca);
        String officeName = name.getText().toString();
        academicBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Save the office name and username to the Record table
                    ((DatabaseHelper) databaseHelper).insertRecords(studentid, officeName);
                    Toast.makeText(AcademicOffices.this, "Office saved as favorite", Toast.LENGTH_SHORT).show();
                } else {
                    // Remove the office name from the Record table if unchecked (optional)
                    ((DatabaseHelper) databaseHelper).deleteRecords(studentid, officeName);
                    Toast.makeText(AcademicOffices.this, "Office removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
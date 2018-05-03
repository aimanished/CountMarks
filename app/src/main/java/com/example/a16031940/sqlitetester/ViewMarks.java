package com.example.a16031940.sqlitetester;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewMarks extends AppCompatActivity {

    ListView lv;
    ArrayAdapter AA;
    ArrayList<Marks> marks;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_marks);
        lv = findViewById(R.id.lv);


        marks = new ArrayList<Marks>();
        myDb = new DatabaseHelper(this);

        Cursor data = myDb.getAllData();

        if (data.getCount() == 0) {
            Toast.makeText(ViewMarks.this, "Nothing to view :(", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                marks.add(new Marks(data.getString(0), data.getString(1), data.getString(2), data.getInt(3)));
            }
            AA = new ViewMarksAdapter(this, R.layout.row, marks);
            lv.setAdapter(AA);

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getBaseContext(),marks.get(position).getName() + " ", Toast.LENGTH_SHORT).show();
                Integer deletedRows = myDb.deleteData(marks.get(position).getId());

                if (deletedRows > 0) {
                    Toast.makeText(ViewMarks.this, "Data is deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewMarks.this, "Data is not deleted please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}

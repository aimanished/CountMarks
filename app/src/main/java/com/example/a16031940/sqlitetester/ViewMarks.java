package com.example.a16031940.sqlitetester;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class ViewMarks extends AppCompatActivity {

    ListView lv;
    ArrayAdapter AA;
    ArrayList<Marks> marks;
    DatabaseHelper myDb;
    Marks marker;
    Switch switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_marks);
        lv = findViewById(R.id.lv);
        marks = new ArrayList<Marks>();
        myDb = new DatabaseHelper(this);
        Cursor data = myDb.getAllData();
        switcher = findViewById(R.id.switch1);
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

                int[] info = {position,0};


                if (switcher.isChecked() == false) {


                    Integer deletedRows = myDb.deleteData(marks.get(position).getId());

                    if (deletedRows > 0) {
                        Toast.makeText(ViewMarks.this, "Data is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewMarks.this, "Data is not deleted please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent i = new Intent(ViewMarks.this, Pop.class);
                    i.putExtra("info",info);
                    startActivity(i);
                }
            }
        });


    }


}

package com.example.a16031940.sqlitetester;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText name;
    EditText surname;
    EditText marks;
    EditText id;
    Button submit;
    Button view;
    Button update;
    Button Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.editText);
        surname = (EditText) findViewById(R.id.editText2);
        marks = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.submit);
        view = (Button) findViewById(R.id.view);
        update = (Button) findViewById(R.id.update);
        id = (EditText) findViewById(R.id.editText4);
        Delete = (Button) findViewById(R.id.buttonDelete);
        AddData();
        ViewAll();
        updatedata();
        DeleteData(); 
    }

    public void DeleteData(){
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mydb.deleteData(id.getText().toString());
                if(deletedRows > 0){
                    Toast.makeText(MainActivity.this, "Data is deleted",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(MainActivity.this, "Data is not deleted please try again",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void updatedata(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isUpdated = mydb.updateData(id.getText().toString(), name.getText().toString(), marks.getText().toString(), surname.getText().toString());
               if(isUpdated == true){
                   Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
               }
               else {
                   Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();

               }
            }
        });
    }

    public void AddData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(name.getText().toString(), surname.getText().toString(), marks.getText().toString());

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                    name.setText("");
                    surname.setText("");
                    marks.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted please try again", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
        public void ViewAll(){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res = mydb.getAllData();
                    if (res.getCount() == 0) {
                        showMessage("Error", "Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Surname :" + res.getString(2) + "\n");
                        buffer.append("Marks :" + res.getString(3) + "\n\n");

                    }

                    // Show all data
                    showMessage("Data", buffer.toString());
                }
            });

        }



    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

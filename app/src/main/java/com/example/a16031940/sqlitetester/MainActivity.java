package com.example.a16031940.sqlitetester;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Button email;
    String messages = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.editText);
        surname = (EditText) findViewById(R.id.editText2);
        marks = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.submit);
        view = (Button) findViewById(R.id.view);
        email = (Button) findViewById(R.id.Email);
        id = (EditText) findViewById(R.id.editText4);
        mydb = new DatabaseHelper(this);

        AddData();
        ViewAll();
        EmailEveryone();

//try
//        SQLiteDatabase sdb = mydb.getWritableDatabase();
//        Cursor cursor = sdb.rawQuery("SELECT * FROM students",null);
//        while(cursor.moveToNext()){
//            String hello = cursor.getString(0);
//Toast.makeText(MainActivity.this,cursor.getString(0) , Toast.LENGTH_LONG).show();        }

        //end
    }

//    public void DeleteData(){
//        Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer deletedRows = mydb.deleteData(id.getText().toString());
//                if(deletedRows > 0){
//                    Toast.makeText(MainActivity.this, "Data is deleted",Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Data is not deleted please try again",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }

//    public void updatedata(){
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//               boolean isUpdated = mydb.updateData(id.getText().toString(), name.getText().toString(), marks.getText().toString(), surname.getText().toString());
//               if(isUpdated == true){
//                   Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
//               }
//               else {
//                   Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
//
//               }
//            }
//        });
//    }

    public void AddData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = mydb.insertData(name.getText().toString(), surname.getText().toString(), marks.getText().toString());

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    surname.setText("");
                    marks.setText("");
                    id.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
        public void ViewAll(){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ViewMarks.class);
                    startActivity(intent);
//                    Cursor res = mydb.getAllData();
//                    if (res.getCount() == 0) {
//                        showMessage("Error", "Nothing found");
//                        return;
//                    }
//
//                    StringBuffer buffer = new StringBuffer();
//                    while (res.moveToNext()) {
//                        buffer.append("Id :" + res.getString(0) + "\n");
//                        buffer.append("Name :" + res.getString(1) + "\n");
//                        buffer.append("Surname :" + res.getString(2) + "\n");
//                        buffer.append("Marks :" + res.getString(3) + "\n\n");
//
//                    }
//
//                    // Show all data
//                    showMessage("Data", buffer.toString());
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

    public void EmailEveryone(){
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the messages.
                SQLiteDatabase sdb = mydb.getWritableDatabase();
                Cursor cursor = sdb.rawQuery("SELECT * FROM students",null);
                while(cursor.moveToNext()){
                  messages += "ID : "+  cursor.getString(0) + " \n";
                    messages += "name : " + cursor.getString(1) + "\n";
                  messages += "surname : " + cursor.getString(2) + "\n";
                    messages += "marks : " + cursor.getString(3) + "\n \n";
                }




                // The action you want this intent to do;
                // ACTION_SEND is used to indicate sending text
                Intent emailE = new Intent(Intent.ACTION_SEND);
                // Put essentials like email address, subject & body text
                emailE.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"@gmail.com"});
                emailE.putExtra(Intent.EXTRA_SUBJECT,
                        "Sending Results");
                emailE.putExtra(Intent.EXTRA_TEXT,
                messages
                        );
                // This MIME type indicates email
                emailE.setType("message/rfc822");
                // createChooser shows user a list of app that can handle
                // this MIME type, which is, email
                startActivity(Intent.createChooser(emailE,
                        "Choose an Email client :"));

            }
        });
    }
}

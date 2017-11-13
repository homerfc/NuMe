package com.example.cmput301f17t27.nume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {
    //UI declarations
    private EditText userName;
    private EditText fullName;
    private Button createButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //UI definitions
        userName = (EditText) findViewById(R.id.username);
        fullName = (EditText) findViewById(R.id.fullname);
        createButton = (Button) findViewById(R.id.createbutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Setup the login button listener
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Do elastic search to create account
                finish();
            }
        });


        //Setup the create account button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }
}

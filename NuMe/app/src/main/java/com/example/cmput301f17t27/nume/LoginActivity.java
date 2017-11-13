package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int CREATE_ACCOUNT_REQUEST_CODE = 105;

    //UI declarations
    private EditText userName;
    private Button loginButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //UI definitions
        userName = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.loginbutton);
        createButton = (Button) findViewById(R.id.createbutton);


        //Setup the login button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Do elastic search and start habitList
            }
        });


        //Setup the create account button listener
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT_REQUEST_CODE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the EditEventActivity
        if (requestCode == CREATE_ACCOUNT_REQUEST_CODE) {
            //Do nothing
        }
    }
}

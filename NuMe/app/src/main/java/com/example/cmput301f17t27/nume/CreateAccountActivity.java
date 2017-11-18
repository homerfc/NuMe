package com.example.cmput301f17t27.nume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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
                //Get the username from the text field
                String userStr = userName.getText().toString();

                //Start the getProfile AsyncTask
                ElasticSearchController.GetProfileTask getProfileTask = new ElasticSearchController.GetProfileTask();
                getProfileTask.execute(userStr);

                try {
                    //Get the getProfile from the task
                    Profile profile = getProfileTask.get();

                    //If the profile exists
                    if(profile != null){
                        //Alert the user
                        Toast newToast = Toast.makeText(getApplicationContext(),
                                "Username already exists, please pick another one.", Toast.LENGTH_SHORT);
                        newToast.show();
                    }

                    //If the profile doesn't exist
                    else{
                        //Get the full name from the text field
                        String nameStr = fullName.getText().toString();

                        //Create the profile object
                        profile = new Profile(userStr, nameStr);

                        //Start the addProfile AsyncTask
                        ElasticSearchController.AddProfileTask addProfileTask = new ElasticSearchController.AddProfileTask();
                        addProfileTask.execute(profile);

                        //Get the ID of the added profile
                        String id = addProfileTask.get();

                        //Set the id of the profile object to the one that was just found
                        profile.setId(id);

                        //Update the profile on ElasticSearch so it has the id member
                        ElasticSearchController.UpdateProfileTask updateProfileTask = new ElasticSearchController.UpdateProfileTask();
                        updateProfileTask.execute(profile);

                        //Return to the login activity
                        finish();
                    }

                }

                catch (Exception e){
                    Toast newToast = Toast.makeText(getApplicationContext(),
                            "Error, please try again.", Toast.LENGTH_SHORT);
                    newToast.show();
                }
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

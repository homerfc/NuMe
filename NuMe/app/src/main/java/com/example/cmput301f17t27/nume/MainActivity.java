package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.ProcessingInstruction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.button_login);
        final Button createAccount = (Button) findViewById(R.id.button_create_account);
        final EditText username = (EditText) findViewById(R.id.userName);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createProfile = new Intent(MainActivity.this, CreateProfileActivity.class);
                startActivity(createProfile);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get username as the parameter
                String UserName = username.getText().toString();
                ElasticsearchProfileController.GetProfileTask getprofileTask = new ElasticsearchProfileController.GetProfileTask();
                getprofileTask.execute(UserName);

                try {
                    ArrayList<Profile> profileList = getprofileTask.get();

                    if(profileList.size() == 1){
                        //Profile testProfile = profileList.get(1);
                        //Log.i("profileName", testProfile.getName());
                        Intent successLogin = new Intent(getApplicationContext(),Main2Activity.class);
                        successLogin.putExtra("username",UserName);
                        startActivity(successLogin);
                    }else{
                        //pop the message state that username is invalid
                        Toast newToast = Toast.makeText(getApplicationContext(), "UserName can't be found", Toast.LENGTH_SHORT);
                        newToast.show();
                    }

                }catch (Exception e){
                    Log.i("Error", "Error getting users out of async object");

                }


            }
        });

    }
}

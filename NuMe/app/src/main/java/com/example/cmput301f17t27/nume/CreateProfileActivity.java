package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        Button createAccount = (Button) findViewById(R.id.CreateAccount);
        final EditText UserName = (EditText) findViewById(R.id.username_creating);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the username is already existed
                //get username as the parameter
                String username = UserName.getText().toString();
                ElasticsearchProfileController.GetProfileTask getprofileTask = new ElasticsearchProfileController.GetProfileTask();
                getprofileTask.execute(username);

                try {
                    ArrayList<Profile> profileList = getprofileTask.get();

                    if(profileList.size() == 1){
                        Toast newToast = Toast.makeText(getApplicationContext(), "User Name already exist, please pick another one", Toast.LENGTH_SHORT);
                        newToast.show();

                    }else{
                        ElasticsearchProfileController.addProfileTask addProfileTask = new ElasticsearchProfileController.addProfileTask();
                        Profile newProfile = new Profile(username);
                        addProfileTask.execute(newProfile);
                        finish();
                    }

                }catch (Exception e){
                    Log.i("Error", "Error getting users out of async object");

                }

            }
        });
    }

}

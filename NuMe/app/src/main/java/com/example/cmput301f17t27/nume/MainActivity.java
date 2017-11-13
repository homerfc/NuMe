package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                ArrayList<Profile> returnedProfileList = new ArrayList<Profile>();
                returnedProfileList = getprofileTask.doInBackground(UserName);

                Profile returnedProfile = returnedProfileList.get(0);
                Log.i("return_value", returnedProfile.getUserName());

                if(returnedProfile!=null){
                    Intent successLogin = new Intent(getApplicationContext(),Main2Activity.class);
                    successLogin.putExtra("username",UserName);
                    startActivity(successLogin);
                }else{
                    //pop the message state that username is invalid
                    Toast newToast = Toast.makeText(getApplicationContext(), "UserName can't be found", Toast.LENGTH_SHORT);
                    newToast.show();
                }

            }
        });
    }
}

package com.example.cmput301f17t27.nume;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        Button createAccount = (Button) findViewById(R.id.CreateAccount);
        final EditText UserUserName = (EditText) findViewById(R.id.username_creating);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the username is already existed
                String UserName = UserUserName.getText().toString();
                Profile newProfile = new Profile(UserName);
                ElasticsearchProfileController.addProfileTask addProfileTask = new ElasticsearchProfileController.addProfileTask();
                addProfileTask.execute(newProfile);
                finish();


//                ElasticsearchProfileController.GetProfileTask getProfileTask = new ElasticsearchProfileController.GetProfileTask();
//                getProfileTask.doInBackground(UserName);
//                ArrayList<Profile> returnedProfileList = new ArrayList<Profile>();
//                returnedProfileList = getProfileTask.doInBackground(UserName);
//                Profile returnedProfile = returnedProfileList.get(0);
//                if(returnedProfile == null)
//                {
//
//                    Profile newProfile = new Profile(UserName);
//                    ElasticsearchProfileController.addProfileTask addProfileTask = new ElasticsearchProfileController.addProfileTask();
//                    addProfileTask.execute(newProfile);
//                    finish();
//                }
//                else
//                {
//                    //pop the message state that username is already exist
//                    Toast newToast = Toast.makeText(getApplicationContext(), "UserName already exist", Toast.LENGTH_SHORT);
//                    newToast.show();
//                }

            }
        });
    }

}

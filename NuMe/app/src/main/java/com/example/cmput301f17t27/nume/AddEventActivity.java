package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AddEventActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int ADD_EVENT_ACTIVITY_RETURN_CODE = 0;
    protected static final int CANCEL_EVENT_ACTIVITY_RETURN_CODE = 1;

    //UI elements
    private EditText comment;
    private EditText image;
    private Switch location;
    private Button addButton;
    private Button cancelButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //UI definitions
        comment = (EditText) findViewById(R.id.comment);
        image = (EditText) findViewById(R.id.image);
        location = (Switch) findViewById(R.id.location);
        addButton = (Button) findViewById(R.id.addbutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Setup the add button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Get the values of the UI elements
                String commentStr = comment.getText().toString();
                String imageStr = image.getText().toString();

                //Bundle up the values and return back to the ViewHabit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("COMMENT", commentStr);
                bundle.putString("IMAGE", imageStr);
                bundle.putBoolean("LOCATION", location.isChecked());
                intent.putExtras(bundle);
                setResult(ADD_EVENT_ACTIVITY_RETURN_CODE, intent);
                finish();
            }
        });

        //Setup the cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Return back to the ViewHabit activity
                Intent intent = new Intent();
                setResult(CANCEL_EVENT_ACTIVITY_RETURN_CODE, intent);
                finish();
            }
        });
    }
}

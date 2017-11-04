package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class EditEventActivity extends AppCompatActivity {
    //Return codes for startingActivityForResult
    protected static final int SAVE_EVENT_ACTIVITY_RETURN_CODE = 2;
    protected static final int DELETE_EVENT_ACTIVITY_RETURN_CODE = 3;
    protected static final int CANCEL_EVENT_ACTIVITY_RETURN_CODE = 1;

    //UI elements
    private EditText comment;
    private EditText image;
    private Switch location;
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //Get the intent that this activity was started with
        Intent intent = getIntent();

        //UI definitions
        comment = (EditText) findViewById(R.id.comment);
        image = (EditText) findViewById(R.id.image);
        location = (Switch) findViewById(R.id.location);
        saveButton = (Button) findViewById(R.id.savebutton);
        deleteButton = (Button) findViewById(R.id.deletebutton);
        cancelButton = (Button) findViewById(R.id.cancelbutton);

        //Set the values of the UI elements to what they were before
        comment.setText(intent.getStringExtra("COMMENT"));
        image.setText(intent.getStringExtra("IMAGE"));
        location.setChecked(intent.getBooleanExtra("LOCATION", false));

        //Setup the save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
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
                bundle.putInt("INDEX", intent.getIntExtra("INDEX", 0));
                intent.putExtras(bundle);
                setResult(SAVE_EVENT_ACTIVITY_RETURN_CODE, intent);
                finish();
            }
        });

        //Setup the delete button listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the index and return back to the ViewHabit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("INDEX", intent.getIntExtra("INDEX", 0));
                intent.putExtras(bundle);
                setResult(DELETE_EVENT_ACTIVITY_RETURN_CODE, intent);
                finish();
            }
        });

        //Setup the cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Bundle up the index and return back to the ViewHabit activity
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("INDEX", intent.getIntExtra("INDEX", 0));
                intent.putExtras(bundle);
                setResult(CANCEL_EVENT_ACTIVITY_RETURN_CODE, intent);
                finish();
            }
        });
    }
}

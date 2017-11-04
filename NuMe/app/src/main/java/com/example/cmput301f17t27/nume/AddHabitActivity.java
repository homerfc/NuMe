package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/*
* AddHabitActivity class created by Dylan Waters on Oct 27, 2017
*
* */

public class AddHabitActivity extends AppCompatActivity {
    private EditText title;
    private EditText reason;
    private EditText date;
    private RadioButton sun;
    private RadioButton mon;
    private RadioButton tues;
    private RadioButton wed;
    private RadioButton thurs;
    private RadioButton fri;
    private RadioButton sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        title = (EditText) findViewById(R.id.editTextHabitTitle);
        reason = (EditText) findViewById(R.id.editTextHabitReason);
        date = (EditText) findViewById(R.id.editTextHabitDate);
        sun = (RadioButton) findViewById(R.id.rBsunday);
        mon = (RadioButton) findViewById(R.id.rBmonday);
        tues = (RadioButton) findViewById(R.id.rBtuesday);
        wed = (RadioButton) findViewById(R.id.rBwednesday);
        thurs = (RadioButton) findViewById(R.id.rBthursday);
        fri = (RadioButton) findViewById(R.id.rBfriday);
        sat = (RadioButton) findViewById(R.id.rBsaturday);
        Button addHabit = (Button) findViewById(R.id.addHabit);

        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Do Something
                //Store with elasticSearch
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}

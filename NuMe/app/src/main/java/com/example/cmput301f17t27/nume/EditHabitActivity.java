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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditHabitActivity extends AppCompatActivity {
    private EditText title;
    private EditText reason;
    private EditText date;
    private CheckBox sun;
    private CheckBox mon;
    private CheckBox tues;
    private CheckBox wed;
    private CheckBox thurs;
    private CheckBox fri;
    private CheckBox sat;
    private ArrayList<String> frequencyList;
    private Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        habit = (Habit) getIntent().getSerializableExtra("HABIT");
        frequencyList = new ArrayList<String>();

        title = (EditText) findViewById(R.id.editTextHabitTitle);
        title.setText(habit.getTitle(), TextView.BufferType.EDITABLE);
        reason = (EditText) findViewById(R.id.editTextHabitReason);
        reason.setText(habit.getReason(), TextView.BufferType.EDITABLE);
        //TODO Fix Date Format
        date = (EditText) findViewById(R.id.editTextHabitDate);
        date.setText(habit.getDateToStart().toString(), TextView.BufferType.EDITABLE);
        //TODO Freq formating
        sun = (CheckBox) findViewById(R.id.cBsunday);
        mon = (CheckBox) findViewById(R.id.cBmonday);
        tues = (CheckBox) findViewById(R.id.cBtuesday);
        wed = (CheckBox) findViewById(R.id.cBwednesday);
        thurs = (CheckBox) findViewById(R.id.cBthursday);
        fri = (CheckBox) findViewById(R.id.cBfriday);
        sat = (CheckBox) findViewById(R.id.cBsaturday);

        Button editHabitSave = (Button) findViewById(R.id.editHabitSave);
        editHabitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

}

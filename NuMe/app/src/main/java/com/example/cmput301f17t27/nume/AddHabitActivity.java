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
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Date;

/**
* AddHabitActivity class created by Dylan Waters on Oct 27, 2017
* Creates a Habit based on the User inputs
* Currently passes variables back and saves them into a file
 * @author Dylan & Xuan
 *
*  @see Main2Activity
* todo Remove the Default Values on the AddHabit XML
* */
public class AddHabitActivity extends AppCompatActivity {
    private EditText title;
    private EditText reason;
    private EditText date;
    private CheckBox sun;
    private CheckBox mon;
    private RadioButton tues;
    private RadioButton wed;
    private RadioButton thurs;
    private RadioButton fri;
    private RadioButton sat;
    private ArrayList<String> frequencyList;

    @Override
    //ToDo Replace all radio buttons to checkbox
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        frequencyList = new ArrayList<String>();

        title = (EditText) findViewById(R.id.editTextHabitTitle);
        reason = (EditText) findViewById(R.id.editTextHabitReason);
        date = (EditText) findViewById(R.id.editTextHabitDate);

        sun = (CheckBox) findViewById(R.id.cBsunday);
        mon = (CheckBox) findViewById(R.id.cBmonday);
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

                //Checking Dates
                if (sun.isChecked()){ frequencyList.add("Sunday");}
                if (mon.isChecked()){ frequencyList.add("Monday");}
                if (tues.isChecked()){ frequencyList.add("Tuesday");}
                if (wed.isChecked()){ frequencyList.add("Wednesday");}
                if (thurs.isChecked()){ frequencyList.add("Thursday");}
                if (fri.isChecked()){ frequencyList.add("Friday");}
                if (sat.isChecked()){ frequencyList.add("Saturday");}
                //find the frequency end;

                //get all the habit info;
                String habit_title = title.getText().toString();
                String habit_reason = reason.getText().toString();
                String Habit_start_date = date.getText().toString();
                String[] splited_date_string = Habit_start_date.split("/");

                int day = Integer.parseInt(splited_date_string[0]); //return int for day
                int month = Integer.parseInt(splited_date_string[1]); //return int for month
                int year = Integer.parseInt(splited_date_string[2]); //return int for year

                Date start_Date = new Date(year,month,day);
                //get all the habit info end;


                //pass to elasticsearch index
                //ElasticsearchController.AddHabitTask addHabitTask = new ElasticsearchController.AddHabitTask();
                //addHabitTask.execute(newHabit)
                Intent intent = new Intent();
                intent.putExtra("title",habit_title);
                intent.putExtra("reason",habit_reason);
                intent.putExtra("date",start_Date);
                //intent.putExtra("date", sDate.getTime());
                intent.putExtra("freq",frequencyList);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}

package com.example.cmput301f17t27.nume;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*imported widget*/
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;


public class HabitListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Request codes for startingActivityForResult
    protected static final int ADD_HABIT_REQUEST_CODE = 100;
    protected static final int VIEW_HABIT_REQUEST_CODE = 101;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int HABIT_ADDED_RESULT_CODE = 1;
    protected static final int HABIT_DELETED_RESULT_CODE = 2;
    protected static final int HABIT_CHANGED_RESULT_CODE = 3;

    //Profile var declaration
    private Profile profile;

    //Index for which habit has been clicked in the list
    private int index;

    //UI declarations
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView nav;
    private View navHeader;
    private TextView fullname;
    private ActionBarDrawerToggle toggle;
    private HabitAdapter adapter;
    private ListView habitList;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        //Setup the drawer and toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);

        //Profile definition
        profile = new Profile("TestUser", "TestName");
        //profile = LocalFileController.loadFromFile(HabitListActivity.this);

        //UI definitions
        navHeader = nav.getHeaderView(0);
        fullname = (TextView) navHeader.findViewById(R.id.fullname);
        habitList = (ListView) findViewById(R.id.habitlist);
        adapter = new HabitAdapter(this, profile.getHabitList());
        addButton = (Button) findViewById(R.id.addbutton);

        //Set the user's name in the drawer
        fullname.setText(profile.getName());

        //Set the adapter for the list of habits
        habitList.setAdapter(adapter);


        //Setup the listener for the list of habits
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Save the index of the habit that was pressed
                index = position;

                //Bundle up the habit and start the view habit activity
                Intent intent = new Intent(HabitListActivity.this, ViewHabitActivity.class);
                Bundle bundle = new Bundle();
                Habit habit = profile.getHabit(position);
                bundle.putSerializable("HABIT", habit);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_HABIT_REQUEST_CODE);
            }
        });


        //Setup the listener for the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitListActivity.this, AddHabitActivity.class);
                startActivityForResult(intent, ADD_HABIT_REQUEST_CODE);
            }
        });
    }



    //Functions for the habit_list_toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.actionsettings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Functions for the drawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habitsbutton) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //TODO
        else if (id == R.id.eventsearchbutton) {

        }

        else if (id == R.id.mapbutton) {

        }

        else if (id == R.id.followersbutton) {

        }

        else if (id == R.id.followeesbutton) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're returning from the AddHabit activity
        if(requestCode == ADD_HABIT_REQUEST_CODE) {
            //If the user clicked the add button
            if(resultCode == HABIT_ADDED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.addHabit(habit);
                adapter.notifyDataSetChanged();
                //LocalFileController.saveToFile(HabitListActivity.this, profile);
            }

            //If the user clicked the cancel button
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the ViewHabit activity
        else if(requestCode == VIEW_HABIT_REQUEST_CODE){
            //If the user changed the habit in any way
            if(resultCode == HABIT_CHANGED_RESULT_CODE) {
                Bundle bundle = data.getExtras();
                Habit habit = (Habit) bundle.getSerializable("HABIT");
                profile.setHabit(index, habit);
                adapter.notifyDataSetChanged();
                //LocalFileController.saveToFile(HabitListActivity.this, profile);
            }

            //If the user deleted the habit
            else if(resultCode == HABIT_DELETED_RESULT_CODE) {
                profile.deleteHabit(index);
                adapter.notifyDataSetChanged();
                //LocalFileController.saveToFile(HabitListActivity.this, profile);
            }

            //If the user didn't change the habit in any way
            else if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }
}

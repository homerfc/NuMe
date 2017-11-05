package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FILENAME = "file.sav";
    private ArrayList<Habit> HabitList = new ArrayList<Habit>();
    private ArrayAdapter<Habit> HabitListAadpter;
    private ListView HabitAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        HabitAdapter = (ListView) findViewById(R.id.HabitAdapter);

        HabitAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this, ViewHabitActivity.class);
                startActivity(intent);
            }
        });

    }

    /*
    *created by xcao2 oct 28.
     */
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        //get result back from elasticsearch
        HabitListAadpter = new ArrayAdapter<Habit>(this, R.layout.list_item,HabitList);
        HabitAdapter.setAdapter(HabitListAadpter);
    }

    /**
     * Loads Counters from a saved file
     * Else creates a new Coutner Array
     */
    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //RenAME
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            HabitList= gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
                HabitList  = new ArrayList<Habit>();
            //throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(HabitList, writer);
            writer.flush();
            //fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.AddHabit) {
            Intent intent =  new Intent(Main2Activity.this, AddHabitActivity.class);
            startActivityForResult(intent, 1);
            // Handle the camera action
        } else if (id == R.id.ViewHabit) {
            Intent intent =  new Intent(Main2Activity.this, ViewHabitActivity.class);
            startActivityForResult(intent, 2);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if(resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String reason = data.getStringExtra("reason");
                Date sDate = (Date) data.getSerializableExtra("date");
                ArrayList freq = data.getStringArrayListExtra("freq");
                Habit habit = new Habit(title,reason,sDate,freq);
                HabitList.add(habit);
                HabitListAadpter.notifyDataSetChanged();
                saveInFile();

            }
            return;
            //Do Something after returning from Add Habit
        }else if(requestCode==2){
            //Do Something after returning from Veiw
            return;
        }
    }
}
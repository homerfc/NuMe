package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
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
import java.io.PipedReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Creates the Main2Activity
 *
 * @author Dylan & Xuan
 * @version 0.1
 * @see AddHabitActivity
 * @since 0.1
 */
public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FILENAME = "file.sav";
    private ArrayList<Habit> HabitList = new ArrayList<Habit>();
    private ArrayAdapter<Habit> HabitListAdapter;
    private ListView HabitAdapter;
    private ArrayList<Profile> ProfileList = new ArrayList<>();



    /**
     * Creates the current apps homepage
     * Gives side bar options and Habit List
     */
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
                Habit habit = HabitList.get(position);
                intent.putExtra("HABIT",habit);
                intent.putExtra("POSITION",position);
                startActivityForResult(intent, 2);
            }
        });

    }

    /**
     * Loads file and sets HabitList
     */
    @Override
    protected void onStart(){
        super.onStart();
        //get the right profile by matching the username; username is from mainActivity
        Intent getIntentExtra = getIntent();
        String UserName = getIntentExtra.getStringExtra("username");
        //ElasticsearchHabitController.GetHabitTask getHabitTask = new ElasticsearchHabitController.GetHabitTask();
        //getHabitTask.execute(UserName);
        ElasticsearchProfileController.GetProfileTask getProfileTask = new ElasticsearchProfileController.GetProfileTask();
        getProfileTask.execute(UserName);

        try{
            //HabitList = getHabitTask.get();
            ProfileList = getProfileTask.get();
        }catch (Exception e){
            Log.i("Error", "Msg");
        }

        //get habits from profile

        Profile profile = ProfileList.get(0);
        HabitList = profile.getHabitList();
        //Log.i("HabitList", String.valueOf(HabitList.size()));


        //put on the adapter
        HabitListAdapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, HabitList);
        HabitAdapter.setAdapter(HabitListAdapter);
    }

    /**
     * Loads Counters from a saved file
     * Else creates a new Coutner Array
     */
//    private void loadFromFile(){
//        try {
//            FileInputStream fis = openFileInput(FILENAME);
//            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
//            Gson gson = new Gson();
//            //RenAME
//            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
//            HabitList= gson.fromJson(in, listType);
//
//        } catch (FileNotFoundException e) {
//                HabitList  = new ArrayList<Habit>();
//            //throw new RuntimeException(e);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * saves Habits to the file
     */
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


    /**
     * Out of Box (OOB)
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * OOB
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    /**
     * OOB
     * @param item
     * @return
     */
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

    /**
     * Side Navigation bar
     * Directs user to new activities
     * @see AddHabitActivity
     * @see ViewHabitActivity
     * @param item
     * @return
     */
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
            //Intent intent =  new Intent(Main2Activity.this, ViewHabitActivity.class);
            //startActivityForResult(intent, 2);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Returns data from the other app pages
     * Creates a new Habit
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            if(resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String reason = data.getStringExtra("reason");
                Date sDate = (Date) data.getSerializableExtra("date");
                ArrayList freq = data.getStringArrayListExtra("freq");

                //this username is from MainActivity when user loged in;
                String owner = getIntent().getStringExtra("username");

                Habit habit = new Habit(title,reason,sDate,freq);


                //get the profile object

                Profile profile = ProfileList.get(0);
                //Log.i("username:",profile.getName());
                profile.addHabit(habit);

                ElasticsearchProfileController.UpdateProfileTask updateProfileTask = new ElasticsearchProfileController.UpdateProfileTask();
                updateProfileTask.execute(profile);


            }
            return;
            //Do Something after returning from Add Habit
        }else if(requestCode==2){
            if(resultCode==400){

                int position = data.getIntExtra("POSITION", 0);
                //Log.i("Deletion?","here: "+position.toString());

                HabitList.remove(position);
                HabitListAdapter.notifyDataSetChanged();
                saveInFile();

            }else{
                Log.i("Deletion?","Failed");
            }
            //Do Something after returning from Veiw
            return;
        }
    }
}

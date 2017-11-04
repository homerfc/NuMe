package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ViewHabitActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int ADD_EVENT_ACTIVITY_REQUEST_CODE = 100;
    protected static final int EDIT_EVENT_ACTIVITY_REQUEST_CODE = 101;

    //Return codes for startingActivityForResult
    protected static final int ADD_EVENT_ACTIVITY_RETURN_CODE = 0;
    protected static final int CANCEL_EVENT_ACTIVITY_RETURN_CODE = 1;
    protected static final int SAVE_EVENT_ACTIVITY_RETURN_CODE = 2;
    protected static final int DELETE_EVENT_ACTIVITY_RETURN_CODE = 3;

    private Habit habit;
    public EventAdapter adapter;
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        //Get the habit object you are viewing with this activity
        habit = (Habit) getIntent().getSerializableExtra("HABIT");

        //Setup the UI list of HabitEvents
        adapter = new EventAdapter(this, habit.getEvents());
        eventList = (ListView) findViewById(R.id.eventlist);
        eventList.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Listener for clicking on items in the HabitEvent list
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewHabitActivity.this, EditEventActivity.class);
                Bundle bundle = new Bundle();

                //Get the HabitEvent object that was clicked
                HabitEvent habitEvent = habit.getEvent(i);

                //Package the HabitEvent object into a bundle
                bundle.putString("COMMENT", habitEvent.getComment());
                bundle.putString("IMAGE", habitEvent.getImage());
                bundle.putString("DATE", habitEvent.getDateCompleted().toString());
                if(habitEvent.getLocation() != null) {
                    bundle.putBoolean("LOCATION", true);
                }
                else {
                    bundle.putBoolean("LOCATION", false);
                }

                //Keep track of the index of the list item that was clicked
                //(so that you know which item to save when you return)
                bundle.putInt("INDEX", i);

                //Go to edit activity
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });

        //Setup the Add Event button and the listener for it
        Button addEventButton = (Button) findViewById(R.id.addeventbutton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(ViewHabitActivity.this, AddEventActivity.class);
                startActivityForResult(intent, ADD_EVENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If you're returning from the AddEventActivity
        if(requestCode == ADD_EVENT_ACTIVITY_REQUEST_CODE) {

            //If the add button was clicked in that activity
            if (resultCode == ADD_EVENT_ACTIVITY_RETURN_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the data
                String comment = bundle.getString("COMMENT");
                String image = bundle.getString("IMAGE");
                boolean location = bundle.getBoolean("LOCATION");

                //Deal with the location separately
                HabitEvent habitEvent;
                if(location) {
                    habitEvent = new HabitEvent(comment, image, getLocation());
                }
                else {
                    habitEvent = new HabitEvent(comment, image);
                }

                //Add the event to the habit
                habit.addEvent(habitEvent);
                adapter.notifyDataSetChanged();
            }

            //If the cancel button was clicked in that activity
            else if(resultCode == CANCEL_EVENT_ACTIVITY_RETURN_CODE) {
                //Do nothing
            }
        }

        //If you're returning from the EditEventActivity
        else if(requestCode == EDIT_EVENT_ACTIVITY_REQUEST_CODE) {

            //If the save button was clicked in that activity
            if (resultCode == SAVE_EVENT_ACTIVITY_RETURN_CODE) {
                Bundle bundle = data.getExtras();

                //Un-bundle the data
                String comment = bundle.getString("COMMENT");
                String image = bundle.getString("IMAGE");
                boolean location = bundle.getBoolean("LOCATION");
                int index = bundle.getInt("INDEX");

                //Get the HabitEvent object that was clicked that
                //brought you to the EditEventActivity
                HabitEvent habitEvent = habit.getEvent(index);

                //Apply the changes to that HabitEvent object
                habitEvent.setComment(comment);
                habitEvent.setImage(image);
                if(location) {
                    habitEvent.setLocation(getLocation());
                }
                else {
                    habitEvent.setLocation(null);
                }
            }

            //If the delete button was clicked in that activity
            else if (resultCode == DELETE_EVENT_ACTIVITY_RETURN_CODE) {
                Bundle bundle = data.getExtras();
                int index = bundle.getInt("INDEX");

                //Delete that event from the Habit object
                habit.deleteEvent(index);
            }

            //If the cancel button was clicked in that activity
            else if(resultCode == CANCEL_EVENT_ACTIVITY_RETURN_CODE) {
                //Do nothing
            }
        }
    }



    private Location getLocation() {
        /*
            This function gets the last known location of the device.

            Args - None
            Returns - A Location object (Null if location services are
                not enabled)
        */

        try   {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            boolean GPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!networkEnabled && !GPSEnabled)    {
                // cannot get location
            }
            else {
                if (GPSEnabled && lm != null)  {
                    return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                else if (networkEnabled && lm!=null) {
                    return lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
        catch (SecurityException ex)  {
            //Security exception
        }
        return null;
    }
}

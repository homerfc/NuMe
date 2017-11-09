package com.example.cmput301f17t27.nume;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates the Habit class
 * @author
 * @version 0.1
 * @see Main2Activity
 * @see AddHabitActivity
 * @see HabitEvent
 * @since 0.1
 */
public class Habit implements Serializable {

    private String title;
    private String reason;
    private Date dateCreated;
    private Date dateToStart;
    private ArrayList<String> frequency;
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();

    /**
     * Creates the Habit Class
     * @param title
     * @param reason
     * @param dateToStart
     * @param frequency
     */
    public Habit( String title, String reason, Date dateToStart, ArrayList frequency) {
        this.title = title;
        this.reason = reason;
        this.dateCreated= new Date();
        this.dateToStart=dateToStart;
        this.frequency=frequency;
    }

    /**
     * Returns Habits title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets habit's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns Habit's reason
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * sets Habit's reason
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * gets Habit's created date
     * @return dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * gets Habit's Start Date
     * @return dateTo Start
     */
    public Date getDateToStart() {
        return dateToStart;
    }

    /**
     *  sets Habit's Start Date
     * @param dateToStart
     */
    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }

    /**
     * ArrayList of Freq. dates
     * @return freq. days
     */
    public ArrayList<String> getFrequency() {
        return this.frequency;
    }

    /**
     * Sets the new freq. dates
     * @param frequency
     */
    public void setFrequency(ArrayList frequency) {
        this.frequency = frequency;
    }

    /**
     * Gets Habit's past events
     * @return events
     */
    public ArrayList<HabitEvent> getEvents() {
        return habitEvents;
    }

    /**
     *  Checks to see if Habit has events
     * @param event
     * @return bool
     */
    public Boolean hasEvent(HabitEvent event) {
        if (habitEvents.contains(event)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Adds a new event to the Habit
     * @param event
     */
    public void addEvent(HabitEvent event) {
        habitEvents.add(event);
    }

    /**
     * Returns the specific event selected
     * @param index
     * @return event
     */
    public HabitEvent getEvent(int index) {
        return habitEvents.get(index);
    }

    /**
     * Deletes the selected event
     * @param index
     */
    public void deleteEvent(int index) {
        habitEvents.remove(index);
    }

    /**
     * Deletes the specific event
     * @param event
     */
    public void deleteEvent(HabitEvent event) {
        habitEvents.remove(event);
    }

    /**
     * Returns some arb stat info
     * todo implementation
     * @param statsType
     * @return stats
     */
    public int viewStats( int statsType ) {
        if (statsType == 0) {
            int numOfEvents = habitEvents.size();
            return numOfEvents;
        } else {
            return -1;
        }
    }
    @Override
    /**
     * Returns the display string for the habit in list views
     */
    public String toString(){
        //toDo add next occurance of this habit
        return "Habit: "+this.title+"\nReason:"+this.reason;
    }


}
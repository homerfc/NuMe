package com.example.cmput301f17t27.nume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matt on 2017-10-18.
 */

public class Habit implements Serializable {

    private String title;
    private String reason;
    private Date dateCreated;
    private Date dateToStart;
    private ArrayList<String> frequency;
    public ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();

    public Habit( String title, String reason, Date dateToStart, ArrayList frequency) {
        this.title = title;
        this.reason = reason;
        this.dateCreated= new Date();
        this.dateToStart=dateToStart;
        this.frequency=frequency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateToStart() {
        return dateToStart;
    }

    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }

    public ArrayList<String> getFrequency() {
        return this.frequency;
    }

    public void setFrequency(ArrayList frequency) {
        this.frequency = frequency;
    }

    public ArrayList<HabitEvent> getEvents() {
        return habitEvents;
    }

    public Boolean hasEvent(HabitEvent event) {
        if (habitEvents.contains(event)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void addEvent(HabitEvent event) {
        habitEvents.add(event);
    }

    public HabitEvent getEvent(int index) {
        return habitEvents.get(index);
    }

    public void deleteEvent(int index) {
        habitEvents.remove(index);
    }

    public void deleteEvent(HabitEvent event) {
        habitEvents.remove(event);
    }

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
     * Returns a string Value of the Habit
     */
    public String toString(){
        //toDo add next occurance of this habit
        return "Habit: "+this.title+"\nReason:"+this.reason;
    }


}

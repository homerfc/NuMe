package com.example.cmput301f17t27.nume;

import java.util.ArrayList;
import java.util.Date;


public class EventCache {
    private ArrayList<HabitEvent> events;
    private int index;
    private Date date;

    public EventCache(ArrayList<HabitEvent> events, int index) {
        this.events = events;
        this.index = index;
    }



    public EventCache(HabitEvent event, int index) {
        events = new ArrayList<HabitEvent>();
        events.add(event);
        this.index = index;
    }



    public HabitEvent getEvent() {
        return events.get(0);
    }



    public ArrayList<HabitEvent> getEvents() {
        return events;
    }



    public int getIndex() {
        return index;
    }
}

package com.example.cmput301f17t27.nume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Matt on 2017-10-20.
 */

public class Profile {
    private String username;
    private String password;
    private String fullName;
    public ArrayList<Habit> habitList = new ArrayList<>();
    public ArrayList<Profile> followingList = new ArrayList<>();
    public ArrayList<Profile> followerList = new ArrayList<>();

    /*
     * constructor with username and password;
     */
    public Profile(String username, String password){
        this.username = username;
        this.password = password;

    }

    /*
     * constructor with username and password and fullName;
     */
    public Profile(String username, String password, String fullName){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }


    public String getUserName(){return this.username;}
    public String getName(){return this.fullName;}
    public void setName(String newName){ fullName = newName;}

    /*
     * Return a sorted history of HabitEvents from all habits
     * or only one habit
     */
    public ArrayList<HabitEvent> habitHistory() {

        ArrayList<HabitEvent> history = new ArrayList<HabitEvent>();

        for (Habit habit : habitList) {
            history.addAll(habit.habitEvents);
        }

        if (history.size() > 0) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;

    }

    public ArrayList<HabitEvent> habitHistory(Habit habit) {

        ArrayList<HabitEvent> history = habit.habitEvents;

        if (history.size() > 0) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    public ArrayList<HabitEvent> habitHistory(String searchFor) {

        ArrayList<HabitEvent> history = new ArrayList<HabitEvent>();

        for (Habit habit : habitList) {
            history.addAll(habit.habitEvents);
        }

        for (HabitEvent event : history) {
            if (!event.getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(event);
            }
        }

        if (history.size() > 0) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    public ArrayList<HabitEvent> habitHistory(Habit habit, String searchFor) {

        ArrayList<HabitEvent> history = habit.habitEvents;

        for (HabitEvent event : history) {
            if (!event.getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(event);
            }
        }

        if (history.size() > 0) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

}

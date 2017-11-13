package com.example.cmput301f17t27.nume;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matt on 2017-10-20.
 */

public class ProfileTest extends ActivityInstrumentationTestCase2 {

    public ProfileTest(){
        super(LoginActivity.class);
    }

    /**
     * Created by xcao2
     * testing the getUserName() method in Profile class
     */
    public void testGetUserName(){
        String username = "Test";
        Profile testProfile = new Profile(username, "Test");
        assertEquals(testProfile.getUserName(), username);
    }

    /**
     * Created by xcao2
     * testing the getName() method in Profile class
     * differ from getUsername() method?
     */
    public void testGetName(){
        String username = "Test";
        Profile testProfile = new Profile(username, "Test");
        assertEquals(testProfile.getName(), username);
    }

    /**
     * Created by xcao2
     * testing the setName() method in Profile class
     * set the name "Ginger", then reset the name via method setName();
     */
    public void testSetName(){
        Profile testProfile = new Profile("Ginger", "Password");
        String newUserName = "Fred";
        testProfile.setName(newUserName);
        assertEquals(testProfile.getName(), newUserName);

    }

    /**
     * Created by xcao2
     * testing the Habit arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    public void testHabitsList(){
        ArrayList<Habit> habitList = new ArrayList<>();
        Date dateToStart = new Date();
        Habit testHabit = new Habit("testTitle","testReason",dateToStart, Habit.Frequency.MONDAY);
        habitList.add(testHabit);
        assertSame(habitList.get(0),testHabit);

    }

    /**
     * Created by xcao2
     * testing the following arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    public void testFollowing(){
        Profile testToFollow = new Profile("Test 1", "Test 1");
        Profile testFollowing = new Profile("Test 2", "Test 2");
        testFollowing.followingList.add(testToFollow);
        assertSame(testFollowing.followingList.get(0), testToFollow);

    }

    /**
     * Created by xcao2
     * testing the follower arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    public void testFollower(){
        Profile testFollowed = new Profile("Test 1", "Test 1");
        Profile testFollower = new Profile("Test 2", "Test 2");
        testFollowed.followerList.add(testFollower);
        assertSame(testFollowed.followerList.get(0), testFollower);

    }

    /**
     * Created by mkluk
     * testing the habitHistory method
     * to see if it returns the HabitEvents in order
     */
    public void testHabitHistory() {

        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, Habit.Frequency.FRIDAY);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, Habit.Frequency.FRIDAY);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, Habit.Frequency.FRIDAY);
        test.habitList.add(habit1);
        test.habitList.add(habit2);
        test.habitList.add(habit3);
        HabitEvent event1 = new HabitEvent();
        HabitEvent event2 = new HabitEvent();
        HabitEvent event3 = new HabitEvent();
        HabitEvent event4 = new HabitEvent();
        HabitEvent event5 = new HabitEvent();
        HabitEvent event6 = new HabitEvent();
        habit1.habitEvents.add(event1);
        habit1.habitEvents.add(event2);
        habit2.habitEvents.add(event3);
        habit2.habitEvents.add(event4);
        habit3.habitEvents.add(event5);
        habit3.habitEvents.add(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event6);
        testEvents.add(event5);
        testEvents.add(event4);
        testEvents.add(event3);
        testEvents.add(event2);
        testEvents.add(event1);
        assertEquals(test.habitHistory(), testEvents);

    }

    public void testHabitHistoryFiltered() {

        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, Habit.Frequency.FRIDAY);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, Habit.Frequency.FRIDAY);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, Habit.Frequency.FRIDAY);
        test.habitList.add(habit1);
        test.habitList.add(habit2);
        test.habitList.add(habit3);
        HabitEvent event1 = new HabitEvent();
        HabitEvent event2 = new HabitEvent();
        HabitEvent event3 = new HabitEvent();
        HabitEvent event4 = new HabitEvent();
        HabitEvent event5 = new HabitEvent();
        HabitEvent event6 = new HabitEvent();
        habit1.habitEvents.add(event1);
        habit1.habitEvents.add(event2);
        habit2.habitEvents.add(event3);
        habit2.habitEvents.add(event4);
        habit3.habitEvents.add(event5);
        habit3.habitEvents.add(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        testEvents.add(event3);
        assertEquals(test.habitHistory(habit2), testEvents);

    }

    public void testHabitHistorySearch() {
        Profile test = new Profile("Test", "Test", "Test");
        Date dateToStart = new Date();
        Habit habit = new Habit("Test 1", "Test 1", dateToStart, Habit.Frequency.FRIDAY);
    }



}

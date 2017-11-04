package com.example.cmput301f17t27.nume;

import android.location.Location;

import java.util.Date;

/**
 * Created by Matt on 2017-10-20.
 */

public class HabitEvent {
    private Date dateCompleted;
    private String comment;
    private String image;
    private Location location;

    public HabitEvent() {

        this.dateCompleted = new Date();

    }

    public HabitEvent( String commentOrImage, int index ) {

        this.dateCompleted = new Date();

        if ( index == 0 ) {
            if ( commentOrImage.length() <= 20 ) {
                this.comment = commentOrImage;
            }
        } else if ( index == 1 ) {
            this.image = commentOrImage;
        }
    }

    public HabitEvent( String comment, String image ) {

        this.dateCompleted = new Date();

        if ( comment.length() <= 20 ) {
            this.comment = comment;
        }

        this.image = image;
    }

    public HabitEvent( String comment, String image, Location location) {

        this.dateCompleted = new Date();

        if ( comment.length() <= 20 ) {
            this.comment = comment;
        }

        this.image = image;
        this.location = location;

     }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public String getComment() {
        return comment;
    }

    public String getImage() {
        return image;
    }

    public Location getLocation() {
        return location;
    }

    public void setDateCompleted() {
        this.dateCompleted = new Date();
    }

    public void setComment(String comment) {
        if (comment.length() <= 20) {
            this.comment = comment;
        }
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

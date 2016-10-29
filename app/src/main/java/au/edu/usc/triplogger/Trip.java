//author: p.schwake
//edited: 2016-10-22 psc
//file: Trip.java
//about: single trip object


package au.edu.usc.triplogger;

import java.util.Date;
import java.util.UUID;

public class Trip {

    private UUID id;
    private String title;
    private Date date;
    private String type;
    private String destination;
    private String duration;
    private String comment;
    private String latitude;
    private String longitude;


    //-------------------------------------------------------------------

    public Trip() {
        this(UUID.randomUUID());
    }

    public Trip(UUID _id) {
        id = _id;
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}

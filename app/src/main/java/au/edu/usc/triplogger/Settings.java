//author: p.schwake
//edited: 2016-10-26 psc
//file: Settings.java
//about: settings object

package au.edu.usc.triplogger;

import java.util.Date;
import java.util.UUID;

public class Settings {
    private UUID uuid;
    private String id;
    private String name;
    private String email;
    private String gender;
    private String comment;

    public Settings() {

        this(UUID.randomUUID());
        name = "Philip Schwake";
        id = "123456";
        email = "email@usc.edu.au";
        gender = "m";
        comment = "42";
    }

    public Settings(UUID _id) {
        uuid = _id;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

//author: p.schwake
//edited: 2016-10-25 psc
//file: SettingsCursorWrapper.java
//about: getter/setter for settings db



package au.edu.usc.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import au.edu.usc.triplogger.Settings;


public class SettingsCursorWrapper extends CursorWrapper{
    public SettingsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Settings getSettings(){
        String uuidString = getString(getColumnIndex(SettingsDbSchema.SettingsTable.Cols.UUID));
        String id = getString(getColumnIndex(SettingsDbSchema.SettingsTable.Cols.ID));
        String name = getString(getColumnIndex(SettingsDbSchema.SettingsTable.Cols.NAME));
        String email = getString(getColumnIndex(SettingsDbSchema.SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(SettingsDbSchema.SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(TripDbSchema.TripTable.Cols.COMMENT));


        Settings settings = new Settings(UUID.fromString(uuidString));
        //Settings settings = new Settings();
        settings.setName(name);
        settings.setId(id);
        settings.setEmail(email);
        settings.setGender(gender);
        settings.setComment(comment);

        return settings;

    }

}

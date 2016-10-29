//author: p.schwake
//edited: 2016-10-25 psc
//file: TripCursorWrapper.java
//about: getter/setter for trip database

package au.edu.usc.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import au.edu.usc.triplogger.Trip;

public class TripCursorWrapper extends CursorWrapper{

    public TripCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Trip getTrip(){
        String uuidString = getString(getColumnIndex(TripDbSchema.TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripDbSchema.TripTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TripDbSchema.TripTable.Cols.DATE));
        String type = getString(getColumnIndex(TripDbSchema.TripTable.Cols.TYPE));
        String destination = getString(getColumnIndex(TripDbSchema.TripTable.Cols.DESTINATION));
        String duration = getString(getColumnIndex(TripDbSchema.TripTable.Cols.DURATION));
        String comment = getString(getColumnIndex(TripDbSchema.TripTable.Cols.COMMENT));
        String latitude = getString(getColumnIndex(TripDbSchema.TripTable.Cols.LATITUDE));
        String longitude = getString(getColumnIndex(TripDbSchema.TripTable.Cols.LONGITUDE));


        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setTitle(title);
        trip.setDate(new Date(date));
        trip.setType(type);
        trip.setDestination(destination);
        trip.setDuration(duration);
        trip.setComment(comment);
        trip.setLatitude(latitude);
        trip.setLongitude(longitude);

        return trip;

    }

}

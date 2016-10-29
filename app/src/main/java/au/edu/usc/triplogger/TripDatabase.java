//author: p.schwake
//edited: 2016-10-22 psc
//file: TripDatabase.java
//about: all about dat database

package au.edu.usc.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import au.edu.usc.triplogger.database.TripBaseHelper;
import au.edu.usc.triplogger.database.TripCursorWrapper;
import au.edu.usc.triplogger.database.TripDbSchema;

public class TripDatabase {

    private static TripDatabase tripDatabase;

    private Context _Context;
    private SQLiteDatabase database;

    public static TripDatabase get(Context context){ //creates new db if it doesnt exist
        if(tripDatabase == null){
            tripDatabase = new TripDatabase(context);
        }
        return tripDatabase;
    }

    private TripDatabase(Context context){
        _Context = context.getApplicationContext();
        database = new TripBaseHelper(_Context).getWritableDatabase();
    }

    private static ContentValues getContentValues(Trip trip) {
        ContentValues values = new ContentValues();
        values.put(TripDbSchema.TripTable.Cols.UUID, trip.getId().toString());
        values.put(TripDbSchema.TripTable.Cols.TITLE, trip.getTitle());
        values.put(TripDbSchema.TripTable.Cols.DATE, trip.getDate().getTime());
        values.put(TripDbSchema.TripTable.Cols.TYPE, trip.getType());
        values.put(TripDbSchema.TripTable.Cols.DESTINATION, trip.getDestination());
        values.put(TripDbSchema.TripTable.Cols.DURATION, trip.getDuration());
        values.put(TripDbSchema.TripTable.Cols.COMMENT, trip.getComment());
        values.put(TripDbSchema.TripTable.Cols.LATITUDE, trip.getLatitude());
        values.put(TripDbSchema.TripTable.Cols.LONGITUDE, trip.getLongitude());

        return values;
    }

    public void addTrip(Trip trip) {
        ContentValues values = getContentValues(trip);
        database.insert(TripDbSchema.TripTable.NAME, null, values);
    }

    public void updateTrip(Trip trip) {
        String uuidString = trip.getId().toString();
        ContentValues values = getContentValues(trip);

        database.update(TripDbSchema.TripTable.NAME, values,
                TripDbSchema.TripTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteTrip(Trip trip){
        String uuidString = trip.getId().toString();
        database.delete(TripDbSchema.TripTable.NAME, TripDbSchema.TripTable.Cols.UUID + " = ?", new String[] { uuidString });
      }

    private TripCursorWrapper queryTrips(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                TripDbSchema.TripTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new TripCursorWrapper(cursor);
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();

        TripCursorWrapper cursor = queryTrips(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            trips.add(cursor.getTrip());
            cursor.moveToNext();
        }
        cursor.close();

        return trips;
    }

    public Trip getTrip(UUID id) {
        TripCursorWrapper cursor = queryTrips(
                TripDbSchema.TripTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTrip();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Trip trip) {
        File externalFilesDir = _Context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, trip.getPhotoFilename());
    }

}

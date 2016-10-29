//author: p.schwake
//edited: 2016-10-22 psc
//file: TripBaseHelper.java
//about: creates/updates trip db

package au.edu.usc.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TripBaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "TripBaseHelper";
    private static final int VERSION = 2;                       //increment manually on eaech update
    private static final String DATABASE_NAME = "tripBase.db";

    public TripBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TripDbSchema.TripTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TripDbSchema.TripTable.Cols.UUID + ", " +
                TripDbSchema.TripTable.Cols.TITLE + ", " +
                TripDbSchema.TripTable.Cols.DATE + ", " +
                TripDbSchema.TripTable.Cols.TYPE + ", " +
                TripDbSchema.TripTable.Cols.DESTINATION + ", " +
                TripDbSchema.TripTable.Cols.DURATION + ", " +
                TripDbSchema.TripTable.Cols.COMMENT + ", " +
                TripDbSchema.TripTable.Cols.LATITUDE + ", " +
                TripDbSchema.TripTable.Cols.LONGITUDE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}

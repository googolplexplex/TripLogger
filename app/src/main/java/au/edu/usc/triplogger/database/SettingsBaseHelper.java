//author: p.schwake
//edited: 2016-10-26 psc
//file: SettingsBaseHelper.java
//about: creates/updates settings database

package au.edu.usc.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsBaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "SettingsBaseHelper";
    private static final int VERSION = 7;                       //increment manually on each update
    private static final String DATABASE_NAME = "settingsBase.db";

    public SettingsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + SettingsDbSchema.SettingsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                SettingsDbSchema.SettingsTable.Cols.UUID + ", " +
                SettingsDbSchema.SettingsTable.Cols.ID + ", " +
                SettingsDbSchema.SettingsTable.Cols.NAME + ", " +
                SettingsDbSchema.SettingsTable.Cols.EMAIL + ", " +
                SettingsDbSchema.SettingsTable.Cols.GENDER + ", " +
                SettingsDbSchema.SettingsTable.Cols.COMMENT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

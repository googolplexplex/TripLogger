//author: p.schwake
//edited: 2016-10-27 psc
//file: SettingsDatabase.java
//about: Control Class for the Settings Database


package au.edu.usc.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import au.edu.usc.triplogger.database.SettingsBaseHelper;
import au.edu.usc.triplogger.database.SettingsCursorWrapper;
import au.edu.usc.triplogger.database.SettingsDbSchema;

public class SettingsDatabase {


    private static SettingsDatabase settingsDatabase;

    private Context _Context;
    private SQLiteDatabase db;

    public static SettingsDatabase get(Context context){ //creates new db if it doesnt exist
        if(settingsDatabase == null){
            settingsDatabase = new SettingsDatabase(context);
        }
        return settingsDatabase;
    }

    private SettingsDatabase(Context context){
        _Context = context.getApplicationContext();
        db = new SettingsBaseHelper(_Context).getWritableDatabase();
    }

    private static ContentValues getContentValues(Settings settings) {
        ContentValues values = new ContentValues();
        values.put(SettingsDbSchema.SettingsTable.Cols.UUID, settings.getUUID().toString());
        values.put(SettingsDbSchema.SettingsTable.Cols.ID, settings.getId());
        values.put(SettingsDbSchema.SettingsTable.Cols.NAME, settings.getName());
        values.put(SettingsDbSchema.SettingsTable.Cols.EMAIL, settings.getEmail());
        values.put(SettingsDbSchema.SettingsTable.Cols.GENDER, settings.getGender());
        values.put(SettingsDbSchema.SettingsTable.Cols.COMMENT, settings.getComment());

        return values;
    }

    public void addSettings(Settings settings) {
        ContentValues values = getContentValues(settings);
        db.insert(SettingsDbSchema.SettingsTable.NAME, null, values);
        updateSettings(settings);
    }

    public void updateSettings(Settings settings) {
        String uuidString = settings.getUUID().toString();
        ContentValues values = getContentValues(settings);

        db.update(SettingsDbSchema.SettingsTable.NAME, values,
                SettingsDbSchema.SettingsTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }


    private SettingsCursorWrapper querySettings(String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                SettingsDbSchema.SettingsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new SettingsCursorWrapper(cursor);
    }


    public Settings getSettings(UUID id) {
        SettingsCursorWrapper cursor = querySettings(
                SettingsDbSchema.SettingsTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();
        } finally {
            cursor.close();
        }
    }

    public Settings getSettings() {
        SettingsCursorWrapper cursor = querySettings(null, null);

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();
        } finally {
            cursor.close();
        }
    }
}

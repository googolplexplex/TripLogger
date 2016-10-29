//author: p.schwake
//edited: 2016-10-25 psc
//file: SettingsDbSchema.java
//about:

package au.edu.usc.triplogger.database;

public class SettingsDbSchema {
    public static final class SettingsTable{
        public static final String NAME = "settings";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String GENDER = "gender";
            public static final String COMMENT = "comment";
        }
    }
}

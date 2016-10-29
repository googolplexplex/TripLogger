//author: p.schwake
//edited: 2016-10-22 psc
//file: TripDbSchema.java
//about: database layout

package au.edu.usc.triplogger.database;

public class TripDbSchema {
    public static final class TripTable{
        public static final String NAME = "trips";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TYPE = "type";
            public static final String DESTINATION = "destination";
            public static final String DURATION = "duration";
            public static final String COMMENT = "comment";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
}

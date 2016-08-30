package com.kichukkhon.android.travelpartner.Database;

import android.provider.BaseColumns;

/**
 * Created by Bridget on 8/24/2016.
 */
public class Tables {
    public final static class TourEntry implements BaseColumns {
        public static final String TOUR_TABLE = "Tour";

        public static final String TOUR_NAME = "tour_name";
        public static final String DESTINATION = "destination";
        public static final String PLACE_ID = "place_id";
        public static final String DESTINATION_LAT = "des_latitude";
        public static final String DESTINATION_LON = "des_longitude";
        public static final String STAR_DATE = "start_time";
        public static final String END_DATE = "end_time";
        public static final String START_LOCATION = "start_location";
        public static final String LOCATION_LAT = "location_lat";
        public static final String LOCATION_LON = "location_lon";
        public static final String TRANSPORT = "transport";
        public static final String BUDGET = "budget";
        public static final String DELETED = "deleted";
    }

    public final static class ExpenseEntry implements BaseColumns {
        public static final String EXPENSE_TABLE = "Expense";

        public static final String TOUR_ID = "tour_id";
        public static final String PURPOSE = "purpose";
        public static final String AMOUNT = "amount";
        public static final String DATE_TIME = "date";

    }

    public final static class AlarmEntry implements BaseColumns {
        public static final String ALARM_TABLE = "Alarm";

        public static final String TOUR_ID = "tour_id";
        public static final String DATE_TIME = "date_time";
        public static final String ALARM_NOTE = "alarm_note";


    }

    public final static class NoteEntry implements BaseColumns {
        public static final String NOTE_TABLE = "Tour_Note";

        public static final String TOUR_ID = "tour_id";
        public static final String TITLE = "title";
        public static final String NOTE = "note";
        public static final String CREATED_AT = "created_at";
    }

    public static final class LocationEntryTable implements BaseColumns {
        public static final String TABLE_NAME = "location_entry";

        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
        public static final String TIME_IN_MILLIS = "time_in_millis";
        public static final String SESSION_ID = "session_id";
    }

    public static final class TravelSessionTable implements BaseColumns {
        public static final String TABLE_NAME = "travel_session";

        public static final String TOUR_ID = "tour_id";
        public static final String START_TIME_IN_MILLIS = "start_time_in_millis";
        public static final String STOP_TIME_IN_MILLIS = "stop_time_in_millis";
    }

    public static final class ImageEntry implements BaseColumns{
        public static final String IMAGE_TABLE="image";

        public static final String TOUR_ID="tour_id";

        public static final String PATH = "path";
        public static final String TITLE = "title";
        public static final String DATETIME = "datetime";

    }


}

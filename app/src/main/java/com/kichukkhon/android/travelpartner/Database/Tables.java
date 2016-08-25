package com.kichukkhon.android.travelpartner.Database;

import android.provider.BaseColumns;

/**
 * Created by Bridget on 8/24/2016.
 */
public class Tables{
    public final static class TourEntry implements BaseColumns{
        public static final String TOUR_TABLE="Tour";

        public static final String TOUR_NAME="tour_name";
        public static final String DESTINATION="destination";
        public static final String PLACE_ID="place_id";
        public static final String DESTINATION_LAT="des_latitude";
        public static final String DESTINATION_LON="des_longitude";
        public static final String STAR_DATE="start_time";
        public static final String END_DATE="end_time";
        public static final String START_LOCATION="start_location";
        public static final String LOCATION_LAT="location_lat";
        public static final String LOCATION_LON="location_lon";
        public static final String TRANSPORT="transport";
        public static final String BUDGET="budget";
        public static final String DELETED="deleted";
    }

    public final static class ExpenseEntry implements BaseColumns{
        public static final String EXPENSE_TABLE="Expense";

        public static final String TOUR_ID="tour_id";
        public static final String PURPOSE="purpose";
        public static final String AMOUNT="amount";
        public static final String DATE_TIME="date_time";

    }

    public final static class AlarmEntry implements BaseColumns{
        public static final String ALARM_TABLE="Alarm";

        public static final String TOUR_ID="tour_id";
        public static final String DATE_TIME="date_time";
        public static final String ALARM_NOTE="note";


    }
    public final static class NoteEntry implements BaseColumns{
        public static final String NOTE_TABLE="Tour_Note";

        public static final String TOUR_ID="tour_id";
        public static final String TITLE="title";
        public static final String NOTE="note";
    }
}

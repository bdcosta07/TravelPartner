package com.kichukkhon.android.travelpartner.Util;

/**
 * Created by Ratul on 8/25/2016.
 */
public final class Constants {

    //place helper strings
    public static final String PLACE_ID_KEY = "place_id";
    public static final String PLACE_NAME_KEY = "place_name";
    public static final String PLACE_LATITUDE_KEY = "place_lat";
    public static final String PLACE_LONGITUDE_KEY = "place_lng";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String CURRENT_TOUR_ID_KEY = "current_tour_id_key";

    //nearby places
    public static final String PLACE_TYPE_ID_KEY = "place_type_id";

    public static final String PLACE_API_KEY = "AIzaSyCzz58Ktc7px0JXqwibVPzqkoNsfwwEZr0";

    public static final long INTERVAL_FOR_NEARBY_LOCATION = 1800000; //30 minutes
    public static final long FASTEST_INTERVAL_FOR_NEARBY_LOCATION = 1200000; //20 minutes

    //route tracker
    public static final long INTERVAL_PERIOD = 420000; // 1000 * 60 * 7 = 7 Minutes
    public static final long FASTEST_INTERVAL_PERIOD = 300000; //1000 * 60 * 5 = 5 Minutes
    public static final float SMALLEST_DISPLACEMENT_METERS = 1.5f; //1.5 meters



}

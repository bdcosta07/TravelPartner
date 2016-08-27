package com.kichukkhon.android.travelpartner.Util;

/**
 * Created by Ratul on 8/27/2016.
 */
public class PlaceUtils {

    private static final String NEARBY_PLACE_SEARCH_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    public static String BuildNearbyPlaceSearchUrl(String locLatLng, String type) {
        StringBuilder sb = new StringBuilder(NEARBY_PLACE_SEARCH_BASE_URL);
        sb.append("json");
        sb.append("?location=" + locLatLng);
        sb.append("&radius=500");
        sb.append("&rankby=distance");
        sb.append("&type=" + type);
        sb.append("&key=" + Constants.PLACE_API_KEY);

        return sb.toString();
    }
}

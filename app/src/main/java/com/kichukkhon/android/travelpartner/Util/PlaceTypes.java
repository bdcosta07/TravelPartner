package com.kichukkhon.android.travelpartner.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ratul on 8/26/2016.
 */
public class PlaceTypes {
    public Map<String, String> placeTypes;

    public PlaceTypes() {
        placeTypes = new HashMap<>();
        addPlaceTypes();
    }

    private void addPlaceTypes() {
        placeTypes.put("atm", "ic_local_atm_white_24dp");
        placeTypes.put("book_store", "ic_local_library_white_24dp");
        placeTypes.put("bus_station", "ic_directions_bus_white_24dp");
        placeTypes.put("cafe", "ic_local_cafe_white_24dp");
        placeTypes.put("department_store", "ic_local_grocery_store_white_24dp");
        placeTypes.put("doctor", "ic_local_hospital_white_24dp");
        placeTypes.put("food", "ic_local_dining_white_24dp");
        placeTypes.put("gas_station", "ic_local_gas_station_white_24dp");
        placeTypes.put("grocery_or_supermarket", "ic_local_grocery_store_white_24dp");
        placeTypes.put("hospital", "ic_local_hospital_white_24dp");
        placeTypes.put("pharmacy", "ic_local_pharmacy_white_24dp");
        placeTypes.put("restaurant", "ic_local_dining_white_24dp");
        placeTypes.put("shopping_mall", "ic_local_mall_white_24dp");
        placeTypes.put("bank", "ic_account_balance_white_24dp");
        placeTypes.put("police", "ic_security_white_24dp");
    }

    public String[] place_type_list = {
            "atm",
            "bank",
            "book_store",
            "bus_station",
            "cafe",
            "department_store",
            "doctor",
            "food",
            "gas_station",
            "hospital",
            "pharmacy",
            "police",
            "restaurant",
            "shopping_mall"
    };
}

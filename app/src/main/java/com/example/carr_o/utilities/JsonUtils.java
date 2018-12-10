package com.example.carr_o.utilities;

import android.util.Log;

import com.example.carr_o.model.VINDecode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<VINDecode> parseJson(String Json, int miles){
        ArrayList<VINDecode> newsItems = new ArrayList<>();

        try {
            JSONObject results = new JSONObject(Json);


            int year = results.getInt("year");
            String make = results.getString("make");
            String model = results.getString("model");
            String trim = results.getString("trim");
            String body_type = results.getString("body_type");
            String transmission = results.getString("transmission");
            String drivetrain = results.getString("drivetrain");
            String engine = results.getString("engine");
            String doors = results.getString("doors");
            String tank_size = results.getString("tank_size");
            String std_seating = results.getString("std_seating");
            String highway_miles = results.getString("highway_miles");
            String city_miles = results.getString("city_miles");

                newsItems.add(new VINDecode(year, make, model, trim, body_type, transmission, drivetrain,
                        engine, doors, tank_size, std_seating, highway_miles, city_miles, miles));

            String car = year + " " + make;
            Log.d("JSON_PARSE", car);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsItems;
    }

}

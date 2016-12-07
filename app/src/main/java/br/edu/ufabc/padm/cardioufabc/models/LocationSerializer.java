package br.edu.ufabc.padm.cardioufabc.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationSerializer {
    public static String serialize(List<LatLng> locations) {
        JSONObject rootNode = new JSONObject();

        try {
            JSONArray locationsJSON = new JSONArray();

            for (LatLng location : locations) {
                JSONObject locationJSON = new JSONObject();

                locationJSON.put("latitude", location.latitude);
                locationJSON.put("longitude", location.longitude);

                locationsJSON.put(locationJSON);
            }

            rootNode.put("locations", locationsJSON);
        } catch (JSONException e) {
            Log.e("ERROR", "Failed to serialize locations", e);
        }

        return rootNode.toString();
    }

    public static List<LatLng> deserialize(String json) {
        List<LatLng> locations = new ArrayList<>();

        try {
            JSONObject rootNode = new JSONObject(json);
            JSONArray locationsJSON = rootNode.getJSONArray("locations");

            for (int i = 0; i < locationsJSON.length(); i++) {
                JSONObject locationJSON = locationsJSON.getJSONObject(i);
                LatLng location = new LatLng(locationJSON.getDouble("latitude"), locationJSON.getDouble("longitude"));
                locations.add(location);
            }

        } catch (JSONException e) {
            Log.e("ERROR", "Failed to deserialize locations", e);
        }

        return locations;
    }
}

package br.edu.ufabc.padm.cardioufabc.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HeartRateSerializer {
    public static String serialize(List<Integer> heartRates) {
        JSONObject rootNode = new JSONObject();

        try {
            JSONArray heartRatesJSON = new JSONArray();

            for (int heartRate : heartRates) {
                heartRatesJSON.put(heartRate);
            }

            rootNode.put("heartRates", heartRatesJSON);
        } catch (JSONException e) {
            Log.e("ERROR", "Failed to serialize heartRates", e);
        }

        return rootNode.toString();
    }

    public static List<Integer> deserialize(String json) {
        List<Integer> heartRates = new ArrayList<>();

        try {
            JSONObject rootNode = new JSONObject(json);
            JSONArray heartRatesJSON = rootNode.getJSONArray("heartRates");

            for (int i = 0; i < heartRatesJSON.length(); i++) {
                heartRates.add(heartRatesJSON.optInt(i));
            }

        } catch (JSONException e) {
            Log.e("ERROR", "Failed to deserialize heartRates", e);
        }

        return heartRates;
    }
}

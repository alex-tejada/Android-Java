package com.developer.tonny.design;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 24/07/17.
 */

public class DataParser {

    private HashMap<String,String> getPlace(JSONObject placeJSON){

        HashMap<String,String> placesList = new HashMap<>();
        String placeName = "";
        String vicinity = "";
        String lat = "";
        String lng = "";
        String reference = "";

        try {
            if (!placeJSON.isNull("name")) {

                placeName = placeJSON.getString("name");

            }
            if (!placeJSON.isNull("vicinity")) {
                vicinity = placeJSON.getString("vicinity");
            }
            lat = placeJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            lng = placeJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = placeJSON.getString("reference");

            placesList.put("place_name",placeName);
            placesList.put("vicinity",vicinity);
            placesList.put("lat",lat);
            placesList.put("lng",lng);
            placesList.put("reference",reference);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return placesList;
    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placesMap = null;

        for (int i=0;i<jsonArray.length();i++)
        {
            try
            {
                placesMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placesMap);


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
       JSONArray jsonArray=null;
        JSONObject jsonObject=null;

        try
        {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}

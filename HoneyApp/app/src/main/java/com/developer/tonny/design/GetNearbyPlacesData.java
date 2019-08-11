package com.developer.tonny.design;

import android.os.AsyncTask;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 24/07/17.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap map;
    String URL;
    Button button_showing;

    @Override
    protected String doInBackground(Object... objects) {

        map = (GoogleMap) objects[0];
        URL = (String) objects[1];
        button_showing = (Button) objects[2];

        DonwloadURL donwload = new DonwloadURL();
        try {
            googlePlacesData = donwload.readURL(URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s)
    {
        List<HashMap<String,String>> nearbyPlaces = null;
        DataParser parser = new DataParser();
        nearbyPlaces = parser.parse(s);
        showNearbyPlaces(nearbyPlaces);
    }

    private void showNearbyPlaces(List<HashMap<String ,String>> nearbyPlaces)
    {
        map.clear();
        for (int i=0;i<nearbyPlaces.size();i++)
        {
            MarkerOptions markerOps = new MarkerOptions();
            HashMap<String,String> googlePlaces=nearbyPlaces.get(i);
            String placeName = googlePlaces.get("place_name");
            String vicinity = googlePlaces.get("vicinity");
            double lat = Double.parseDouble(googlePlaces.get("lat"));
            double lng = Double.parseDouble(googlePlaces.get("lng"));
            LatLng ltlg = new LatLng(lat,lng);
            markerOps.position(ltlg);
            markerOps.title(placeName+": "+vicinity);
            markerOps.icon(BitmapDescriptorFactory.fromResource(R.drawable.pillbottle));
            map.addMarker(markerOps);
        }
        button_showing.setEnabled(true);
    }
}

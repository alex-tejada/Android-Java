package com.developer.tonny.design.Activities;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.tonny.design.GetNearbyPlacesData;
import com.developer.tonny.design.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Clase para abrir la ventana del Mapa
 * @author Alejandro
 * @version 1.0
 */
public class mapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapa;
    LatLng pos;
    Button button_showPlaces;

    private LatLng posicion;
    private Marker marcador;
    boolean moveCam=false;
    Location tempLoc;
    boolean showFlag=false;
    boolean showInPorcess=false;
    Thread gpsActiveThread;

    /**
     * Constructor de la clase
     * @param savedInstanceState Bundel Datos de componentes
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.fragmentmap);
        mf.getMapAsync(this);
        setToolbar();
        setTitle("My HoneyApp");

        button_showPlaces = (Button) findViewById(R.id.button_showPlaces);

        button_showPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                button_showPlaces.setEnabled(false);
                showInPorcess=true;
                Toast.makeText(getApplicationContext(),R.string.messageGPS,Toast.LENGTH_LONG).show();

                if(gpsActiveThread==null)
                {
                    startThread();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(gpsActiveThread!=null)
        {
                gpsActiveThread=null;
        }
    }

    public void startThread (){

        gpsActiveThread = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    if(showInPorcess&&tempLoc!=null)
                    {
                        showInPorcess=false;
                        try
                        {
                            setLocations(tempLoc);
                        }
                        catch (Exception e)
                        {

                        }
                    }
                    try
                    {
                        Thread.sleep(10);
                    } catch (InterruptedException e)
                    {

                    }
                }

            }
        };
        gpsActiveThread.start();
    }

    public void setLocations(Location loc)
    {
        String url = getUrl(loc);
        Object dataTransfer[] = new Object[3];
        dataTransfer[0] = mapa;
        dataTransfer[1] = url;
        dataTransfer[2] = button_showPlaces;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        Toast.makeText(getApplicationContext(),R.string.messageShowing,Toast.LENGTH_LONG).show();

    }

    public String getUrl(Location loc)
    {

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + loc.getLatitude() + "," + loc.getLongitude());
        sb.append("&radius=1500");
        sb.append("&types=" + "pharmacy");
        sb.append("&sensor=true");
        sb.append("&key="+"AIzaSyAcHS1dV0f0I1NZCTe9uEcViZHlh5Ddp44");

        Log.d("Map", "api: " + sb.toString());

        return sb.toString();
    }

    /**
     * Metodo para inicializar el mapa
     * @param map GoogleMap de mapa creado
     */
    @Override
    public void onMapReady(GoogleMap map)
    {
        mapa=map;
        mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Ubicar();
    }

    /**
     * Metodo para recibir ubicacion de GPS
     */
    private void Ubicar()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Habilita el permiso desde ajustes", Toast.LENGTH_LONG).show();
            return;
        }

        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // Actualización del mapa
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        actualizarMapa(loc);
    }

    /**
     * Metodo para agregar el marcador en una localizacion del mapa
     * @param loc Location Objeto que contiene la localizacion
     */
    public void actualizarMapa(Location loc) {
        if (loc != null)
        {
            posicion = new LatLng(loc.getLatitude(), loc.getLongitude());
            if (marcador != null) {
                marcador.remove();
            }
            marcador = mapa.addMarker(new MarkerOptions().position(posicion).
                    title(getString(R.string.tittleLocation)).
                    icon(BitmapDescriptorFactory.
                            fromResource(R.drawable.marcador)));
            if(moveCam==false)
            {
                mapa.moveCamera(CameraUpdateFactory.newLatLng(posicion));
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,14));
                moveCam=true;
            }
            //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 18));

        }
    }

    LocationListener locListener = new LocationListener()
    {
        /**
         * Metodo para recibir la localizacion del GPS
         * @param location Location Locaclizacion actual del GPS
         */
        @Override
        public void onLocationChanged(Location location)
        {
            showFlag=true;
            actualizarMapa(location);
            tempLoc=location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    /**
     * Metodo para crear la barra de herramientas
     */
    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

package com.example.root.aplicacionbasedatos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.root.aplicacionbasedatos.R.drawable.distance;

public class PasosActivity extends AppCompatActivity {

    Button button_steps,button_diet,button_exercise,button_details,button_saveRecord;


    TextView textView_pasos,textView_distancia,textView_calorias, textView_Ejercicio,textView_Dieta;


    boolean start=false,OnMoving=false;
    Location locationStart,locationOnMoving;
    int totalSteps=0;
    double lastDistanceM=-1;
    double distanceRecorred=0;
    double tempDistanceM=0;

    String calorieAmount="";
    String stepsAmount="";
    String travelledAmount="";
    String username;

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos);
        setTitle("Step Counter PRO");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button_steps = (Button) findViewById(R.id.button_Round);
        button_diet = (Button) findViewById(R.id.button_Dieta);
        button_exercise = (Button) findViewById(R.id.button_Ejercicio);
        button_details = (Button) findViewById(R.id.button_Details);
        button_saveRecord = (Button) findViewById(R.id.button_SaveRecord);

        textView_calorias = (TextView)findViewById(R.id.textView_Calorias);
        textView_distancia = (TextView) findViewById(R.id.textView_Distancia);
        textView_pasos = (TextView) findViewById(R.id.textView_Pasos);
        textView_Dieta = (TextView) findViewById(R.id.textView_dieta);
        textView_Ejercicio = (TextView) findViewById(R.id.textView_ejercicio);

        button_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(getApplicationContext(),InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                inte.putExtras(bundle);
                startActivity(inte);
            }
        });
        button_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(getApplicationContext(),DietActivity.class);
                startActivity(inte);
            }
        });
        button_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(getApplicationContext(),EjercicioActivity.class);
                startActivity(inte);
            }
        });
        button_steps.setText(R.string.pasosPresioneBoton);
        button_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!start)
                {
                    start=true;
                    textView_pasos.setText(getString(R.string.pasosPasos)+"\n0");
                    textView_distancia.setText(getString(R.string.pasosDistancia)+"\n0");
                    textView_calorias.setText(getString(R.string.pasosCalorias)+"\n0");
                    Toast.makeText(getApplicationContext(),R.string.pasosMensajeAct,Toast.LENGTH_SHORT).show();
                    button_steps.setEnabled(false);
                    button_saveRecord.setVisibility(View.INVISIBLE);
                    Ubicar();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),R.string.pasosMensajeDesac,Toast.LENGTH_SHORT).show();
                    stopStepCounter();
                }
            }
        });

        if(MainActivity.usuarioLogueado)
        {
            username = getIntent().getExtras().getString("username");
            button_exercise.setVisibility(View.VISIBLE);
            button_diet.setVisibility(View.VISIBLE);
            textView_Ejercicio.setVisibility(View.VISIBLE);
            textView_Dieta.setVisibility(View.VISIBLE);
            button_details.setVisibility(View.VISIBLE);
        }
        else
        {
            button_exercise.setVisibility(View.VISIBLE);
            button_saveRecord.setVisibility(View.VISIBLE);
            button_diet.setVisibility(View.VISIBLE);
            textView_Ejercicio.setVisibility(View.VISIBLE);
            textView_Dieta.setVisibility(View.VISIBLE);
            button_details.setVisibility(View.VISIBLE);
        }
        button_saveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();

                String userEncrypt = encriptar.encrypt(username);
                String dateEncrypt = encriptar.encrypt(""+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR));
                String stepsEncrypt = encriptar.encrypt(stepsAmount);
                String calorieEncrypt = encriptar.encrypt(calorieAmount);
                String travelledEncrypt = encriptar.encrypt(travelledAmount+"m");

                saveRecordsINSERT(userEncrypt,dateEncrypt,stepsEncrypt,calorieEncrypt,travelledEncrypt);
                button_saveRecord.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void saveRecordsINSERT(String user,String date,String steps,String calorie,String travelled)
    {
      String responseString = cn.recordsINSERT("http://alexurie.x10host.com/INSERTrecordsSC.php", user,date,steps,calorie,travelled);
        Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.usuarioLogueado=false;
    }

    private void Ubicar()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), "Habilita el permiso desde ajustes", Toast.LENGTH_LONG).show();
            return;
        }
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        button_steps.setText(R.string.pasosIniciandoBoton);


    }

    public void distanceTraveled()
    {
        double distanceM = locationStart.distanceTo(locationOnMoving);

        if(distanceM>=0.72&&distanceM<=4&&lastDistanceM!=distanceM)
        {
            if(lastDistanceM!=-1) {
                totalSteps += 1;
            }
            lastDistanceM=distanceM;
            locationStart=locationOnMoving;
            distanceRecorred+=0.72;
        }

        button_steps.setEnabled(true);
        textView_pasos.setVisibility(View.VISIBLE);
        textView_distancia.setVisibility(View.VISIBLE);
        textView_calorias.setVisibility(View.VISIBLE);

        button_steps.setText(R.string.pasosDetenerBoton);
        textView_pasos.setText(getString(R.string.pasosPasos)+"\n"+totalSteps);
        textView_distancia.setText(getString(R.string.pasosDistancia)+"\n"+(int)distanceRecorred+"m");
        textView_calorias.setText(getString(R.string.pasosCalorias)+"\n"+(int)(distanceRecorred*0.2388));

    }
    public void stopStepCounter()
    {
        button_steps.setText(R.string.pasosReiniciarBoton);
        if(MainActivity.usuarioLogueado&&totalSteps>0)
        {
            button_saveRecord.setVisibility(View.VISIBLE);
        }
        calorieAmount = String.valueOf((int)(distanceRecorred*0.2388));
        stepsAmount = String.valueOf(totalSteps);
        travelledAmount = String.valueOf((int)distanceRecorred);
        start=false;
        totalSteps=0;
        lastDistanceM=-1;
        tempDistanceM=0;
        distanceRecorred=0;
        locationOnMoving=null;
        locationStart=null;
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location)
        {
            if(start)
            {
                if (locationStart == null) {
                    locationStart = location;
                }
                locationOnMoving = location;
                distanceTraveled();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {

        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }
    };
}

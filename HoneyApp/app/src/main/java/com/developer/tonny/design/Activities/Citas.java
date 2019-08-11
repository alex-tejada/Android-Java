package com.developer.tonny.design.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.developer.tonny.design.R;

import java.util.Calendar;
import java.util.Locale;

public class Citas extends AppCompatActivity {

    Button btnAgregar, button_fechacap,button_horacap;
    EditText editText_nombreNota, editText_descripcion;

    int ano=0,dia=0,mes=0,minutos=0,horas=0;
    String id="";
    static final int DIALOG_ID=1,DIALOG_TIME=2;
    Calendar calendar = Calendar.getInstance();
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAgregar = (Button) findViewById(R.id.btnAgregarCit);
        button_fechacap = (Button) findViewById(R.id.button_FechaCap);
        button_horacap = (Button) findViewById(R.id.button_HoraCap);
        editText_nombreNota = (EditText) findViewById(R.id.editText_NombreNota);
        editText_descripcion = (EditText) findViewById(R.id.editText_Descripcion);

        button_fechacap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(DIALOG_ID);
            }
        });
        button_horacap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(DIALOG_TIME);

            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ano!=0&&horas!=0&&!editText_nombreNota.getText().toString().equals(""))
                {
                    if(!flag) {
                        datesINSERT(editText_nombreNota.getText().toString(),
                                dia+"/"+mes+"/"+ano
                                , horas + ":" + minutos
                                , editText_descripcion.getText().toString());
                    }
                    else
                    {
                        setDatesUPDATE(editText_nombreNota.getText().toString(),
                                dia+"/"+mes+"/"+ano,
                                horas + ":" + minutos,
                                editText_descripcion.getText().toString());
                    }
                }
            }
        });

        ComprobarIdioma();
        Comprobar(MainActivity.Iniciado);
        if(getIntent().getExtras()!=null)
        {
            try {
                setDatesMode(getIntent().getExtras().getString("name"),
                        getIntent().getExtras().getString("date"),
                        getIntent().getExtras().getString("hour"),
                        getIntent().getExtras().getString("description"),
                        getIntent().getExtras().getString("id"));
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        setToolbar();
    }

    public void setDatesMode(String name,String date, String hour,String description,String id)
    {
        editText_descripcion.setText(description);
        editText_nombreNota.setText(name);
        dia = Integer.parseInt(date.substring(0,date.indexOf("/")));
        mes = Integer.parseInt(date.substring(date.indexOf("/")+1,date.lastIndexOf("/")));
        ano = Integer.parseInt(date.substring(date.lastIndexOf("/")+1,date.length()));
        horas= Integer.parseInt(hour.substring(0,hour.indexOf(":")));
        minutos =Integer.parseInt(hour.substring(hour.indexOf(":")+1,hour.length()));

        this.id = id;
        flag=true;
        btnAgregar.setText(R.string.btnAgregarCitUp);
    }

    public void datesINSERT(String name, String date,String hour, String description)
    {
        boolean resultINSERT=false;
        try {
            resultINSERT = MainActivity.db.setDatesINSERT(name,
                    date,
                    hour,
                    description,
                    MainActivity.usuarioNombre);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        if(resultINSERT)
        {
            Toast.makeText(getApplicationContext(),R.string.Added,Toast.LENGTH_LONG).show();
        }
    }

    public void setDatesUPDATE(String name, String date,String hour, String description)
    {
        boolean resultUPDATE=false;
        try {
            resultUPDATE = MainActivity.db.setDatesUPDATE(id,
                    name,
                    date,
                    description,
                    hour);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        if(resultUPDATE)
        {
            Toast.makeText(getApplicationContext(),R.string.Updated,Toast.LENGTH_LONG).show();
        }
    }

    DatePickerDialog.OnDateSetListener dpdlistener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    ano=year;
                    mes=month;
                    dia=dayOfMonth;
                }
            };

    TimePickerDialog.OnTimeSetListener tpdlistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            minutos=minute;
            horas=hourOfDay;

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,
                    dpdlistener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        else if (id==DIALOG_TIME)
            return new TimePickerDialog(this,
                    tpdlistener,
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    false);
            else
            return null;
    }

    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void Comprobar(boolean usertype)
    {
        if(usertype){
            btnAgregar.setEnabled(true);
        }
        else{
            btnAgregar.setEnabled(false);
        }
    }
    public void ComprobarIdioma(){
        String Lang = Locale.getDefault().getLanguage();

        if(Lang.equals("en"))
        {
            setTitle("Dates");

        }
        else{
            setTitle("Citas");
        }
    }
}

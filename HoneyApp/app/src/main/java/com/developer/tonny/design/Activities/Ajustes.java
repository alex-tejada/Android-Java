package com.developer.tonny.design.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.developer.tonny.design.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.attr.name;
import static com.developer.tonny.design.Activities.MainActivity.db;

/**
 * Clase para abrir la ventana de ajustes
 * @author Alejandro
 * @version 1.0
 */
public class Ajustes extends AppCompatActivity {

    Button btnGuardar,button_fecha;
    EditText editText_estatura,editText_peso, editText_nombre,editText_numero;
    Spinner spinner_estatura,spinner_peso;

    Calendar calendar= Calendar.getInstance();

    int ano,dia,mes;
    static final int DIALOG_ID=0;

    boolean flag=false;
    String[] estaturas={"'ft","'cm"};
    String[] pesos ={"'lb","'kg"};

    ArrayAdapter adapter;
    ArrayAdapter adapterPeso;
    /**
     * Constructor de clase
     * @param savedInstanceState Bundle Datos de componentes de ventana
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnGuardar = (Button) findViewById(R.id.btnGuardarInf);
        button_fecha = (Button) findViewById(R.id.button_Fecha);

        editText_estatura = (EditText) findViewById(R.id.editText_Estatura);
        editText_peso = (EditText) findViewById(R.id.editText_Peso);
        editText_nombre=(EditText) findViewById(R.id.txtNombreInf);
        editText_numero =(EditText) findViewById(R.id.editText_NumeroEmergencia);

        editText_estatura.setHint(R.string.editText_Estatura);
        editText_peso.setHint(R.string.editText_Peso);

        spinner_estatura= (Spinner) findViewById(R.id.spinner_Estatura);
        spinner_peso= (Spinner) findViewById(R.id.spinner_Peso);

        adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,estaturas);
        spinner_estatura.setAdapter(adapter);

        adapterPeso = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,pesos);
        spinner_peso.setAdapter(adapterPeso);

        button_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Calendar cal = Calendar.getInstance();

                String peso=editText_peso.getText().toString();
                String estatura=editText_estatura.getText().toString();
                String nombre=editText_nombre.getText().toString();
                String numero = editText_numero.getText().toString();
                String fecha = ano+"-"+mes+"-"+dia;

                if(!peso.equals("")&&!estatura.equals("")&&!nombre.equals("")&&!numero.equals("")&&!fecha.equals("")&&ano!=0&&ano<cal.get(Calendar.YEAR))
                {

                    String edad = getAge();
                    peso=peso+spinner_peso.getSelectedItem().toString();
                    estatura=estatura+spinner_estatura.getSelectedItem().toString();
                    setUserinfoUPDATE(nombre,fecha,estatura,peso,edad,numero);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),R.string.InfoUser,Toast.LENGTH_LONG).show();
                }

            }
        });

        Comprobar(MainActivity.Iniciado);
        setToolbar();
        getUserinfoData(MainActivity.usuarioNombre);

    }

    public String getAge()
    {
        String age="";
        Calendar calendar = Calendar.getInstance();
        Calendar calendarDate = Calendar.getInstance();

        calendar.set(ano,Calendar.MONTH,Calendar.DAY_OF_WEEK);
        calendarDate.set(ano,mes,dia);

        int datesDif= calendar.compareTo(calendarDate);

        calendar=Calendar.getInstance();

        if(datesDif<0)
        {
            age=String.valueOf(calendar.get(Calendar.YEAR)-ano);
        }
        else
        {
            age=String.valueOf(calendar.get(Calendar.YEAR)-ano+1);
        }

        return age;
    }

    public void setUserinfoData(String[] dataFromRow)
    {
        String pesoUnit="";
        String estaturaUnit="";
        editText_nombre.setText(dataFromRow[0]);
        editText_numero.setText(dataFromRow[4]);

        if(!dataFromRow[3].equals(""))
        {
            editText_peso.setText(dataFromRow[3].substring(0, dataFromRow[3].indexOf("'")));
            pesoUnit = dataFromRow[3].substring(dataFromRow[3].indexOf("'"),dataFromRow[3].length());

        }
        if(!dataFromRow[2].equals(""))
        {
            editText_estatura.setText(dataFromRow[2].substring(0, dataFromRow[2].indexOf("'")));
            estaturaUnit = dataFromRow[2].substring(dataFromRow[2].indexOf("'"),dataFromRow[2].length());

        }


        for (int i=0;i<adapter.getCount();i++)
        {
           if(estaturas[i].equals(estaturaUnit))
           {
              spinner_estatura.setSelection(i);
           }
            if(pesos[i].equals(pesoUnit))
            {
                spinner_peso.setSelection(i);
            }
        }

        if(!dataFromRow[2].equals(""))
        {
            ano = Integer.parseInt(dataFromRow[1].substring(0, dataFromRow[1].indexOf("-")));
            mes = Integer.parseInt(dataFromRow[1].substring(dataFromRow[1].indexOf("-") + 1, dataFromRow[1].lastIndexOf("-")));
            dia = Integer.parseInt(dataFromRow[1].substring(dataFromRow[1].lastIndexOf("-") + 1, dataFromRow[1].length()));
        }
    }

    public void getUserinfoData(String user)
    {
        Cursor cursor =db.getUserinfoSELECT(user);

        if(cursor.getCount()==0)
        {

        }
        else
        {
            String [] dataFromRow=new String[6];
            while (cursor.moveToNext())
            {
                dataFromRow[0]=cursor.getString(1);
                dataFromRow[1]=cursor.getString(2);
                dataFromRow[2]=cursor.getString(3);
                dataFromRow[3]=cursor.getString(4);
                dataFromRow[4]=cursor.getString(5);
                dataFromRow[5]=cursor.getString(6);
            }
            try {
                setUserinfoData(dataFromRow);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setUserinfoUPDATE(String name,String date,String height, String weight, String age,String phone)
    {
        boolean resultUPDATE=false;
        try
        {
            resultUPDATE = db.setUserinfoUPDATE(name, date, height, weight, phone, age, MainActivity.usuarioNombre);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        if(resultUPDATE)
        {
            Toast.makeText(getApplicationContext(), R.string.Updated, Toast.LENGTH_LONG).show();
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

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
            return new DatePickerDialog(this,
                    dpdlistener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        else
            return null;
    }


    /**
     * Metodo para agregar barra de herramientas
     */
    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Metodo para comprobar el tipo de usuario
     * @param usertype boolean Usuario valido o registrado
     */
    public void Comprobar(boolean usertype)
    {
         if(usertype){
             btnGuardar.setEnabled(true);
         }
         else{
             btnGuardar.setEnabled(false);
         }
    }
}

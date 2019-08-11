package com.developer.tonny.design.Activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.developer.tonny.design.R;

/**
 * Clase para abrir la ventana de calculadora
 * @author Alejandro
 * @version 1.0
 */
public class Medicamentos extends AppCompatActivity {

    Button btnAgregar;
    EditText editText_name,editText_amount;
    CheckBox checkBox_lunes,checkBox_martes,checkBox_miercoles,checkBox_jueves,checkBox_viernes,checkBox_sabado,checkBox_domingo;

    String []Cantidad={"ml","mg"};
    ArrayAdapter adapter;
    ArrayAdapter adapterCantidad;

    boolean flag = false;
    String id;

    Spinner spinner_cada,spinner_cantidad,spinner;
    /**
     * Constructor de la clase
     * @param savedInstanceState Bundel Datos de componentes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAgregar = (Button) findViewById(R.id.btnAgregarMed);

        editText_amount = (EditText) findViewById(R.id.editText_Amount);
        editText_name = (EditText) findViewById(R.id.editText_Name);

        checkBox_domingo = (CheckBox) findViewById(R.id.checkBox_Domingo);
        checkBox_jueves = (CheckBox) findViewById(R.id.checkBox_Jueves);
        checkBox_lunes = (CheckBox) findViewById(R.id.checkBox_Lunes);
        checkBox_martes = (CheckBox) findViewById(R.id.checkBox_Martes);
        checkBox_miercoles = (CheckBox) findViewById(R.id.checkBox_Miercoles);
        checkBox_sabado = (CheckBox) findViewById(R.id.checkBox_Sabado);
        checkBox_viernes = (CheckBox) findViewById(R.id.checkBox_Viernes);

        spinner_cada = (Spinner) findViewById(R.id.spinner_Cada);
        spinner_cantidad = (Spinner) findViewById(R.id.spinner_Cantidad);

        adapterCantidad = new ArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item,
                Cantidad);
        spinner_cantidad.setAdapter(adapterCantidad);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spinner_Cada));
        spinner_cada.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String amount = editText_amount.getText().toString();
                String name = editText_name.getText().toString();

                if(checkBox_viernes.isChecked()||
                        checkBox_sabado.isChecked()||
                        checkBox_miercoles.isChecked()||
                        checkBox_jueves.isChecked()||
                        checkBox_lunes.isChecked()||
                        checkBox_martes.isChecked()||
                        checkBox_miercoles.isChecked()&&
                                !amount.equals("")&&
                                !name.equals(""))
                {
                    String date="";
                    if(checkBox_viernes.isChecked()){ date += checkBox_viernes.getText().toString(); }
                    if(checkBox_sabado.isChecked()){ date += " "+checkBox_sabado.getText().toString(); }
                    if(checkBox_domingo.isChecked()){ date += " "+checkBox_domingo.getText().toString(); }
                    if(checkBox_martes.isChecked()){ date += " "+checkBox_martes.getText().toString(); }
                    if(checkBox_miercoles.isChecked()){ date += " "+checkBox_miercoles.getText().toString(); }
                    if(checkBox_jueves.isChecked()){ date += " "+checkBox_jueves.getText().toString(); }

                    if(!flag)
                    {
                          medicineINSERT(name,amount,date);
                    }
                    else
                    {
                        try {
                            medicineUPDATE(name, amount, date);
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else if(amount.equals("")||name.equals(""))
                {

                    Toast.makeText(getApplicationContext(),R.string.errorFillSpaces,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),R.string.errorFillSpaces,Toast.LENGTH_LONG).show();
                }
            }
        });

        Comprobar(MainActivity.Iniciado);
        setTitle(R.string.textView_MedicamentosTit);
        setToolbar();

        if(getIntent().getExtras()!=null)
        {
            try {
                setMedicineMode();
            }catch (Exception e)
            {
               Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
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
            btnAgregar.setEnabled(true);
        }
        else{
            btnAgregar.setEnabled(false);
        }
    }

    public void setMedicineMode()
    {
        editText_name.setText(getIntent().getExtras().getString("name"));
        editText_amount.setText(getIntent().getExtras().getString("amount"));
        String amountUnit = getIntent().getExtras().getString("amountUnit");
        id=getIntent().getExtras().getString("id");
        String spinnerItem= getIntent().getExtras().getString("hour");

        for (int i=0;i<adapter.getCount();i++)
        {
              if(adapter.getItem(i).toString().equals(spinnerItem))
              {
                  spinner_cada.setSelection(i);
                  break;
              }
        }
        for (int i=0;i<adapterCantidad.getCount();i++)
        {
            if(adapterCantidad.getItem(i).toString().equals(amountUnit))
            {
                spinner_cantidad.setSelection(i);
                break;
            }
        }
        btnAgregar.setText(R.string.btnAgregarCitUp);
        flag=true;
    }

    public void medicineINSERT(String name, String amount, String date)
    {
        boolean resultINSERT=false;
        try {
            resultINSERT = MainActivity.db.setMedicineINSERT(name,
                    date,
                    spinner_cada.getSelectedItem().toString(),
                    amount + " " + spinner_cantidad.getSelectedItem().toString(),
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

    public void medicineUPDATE(String name, String amount, String date)
    {
        boolean resultUPDATE=false;
        try {
            resultUPDATE = MainActivity.db.setMedicineUPDATE(id,
                    name,
                    date,
                    amount + "" + spinner_cantidad.getSelectedItem().toString(),
                    spinner_cada.getSelectedItem().toString());
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
}

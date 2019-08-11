package com.developer.tonny.design.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.tonny.design.Activities.MainActivity;
import com.developer.tonny.design.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.description;
import static android.R.attr.name;
import static com.developer.tonny.design.Activities.MainActivity.db;
import static com.developer.tonny.design.R.id.editText_Glucosa;
import static com.developer.tonny.design.R.id.editText_InsulinaInf;
import static com.google.android.gms.common.api.Status.st;

/**
 * Clase para manejo de pantallas
 * @author Alejandro
 * @version 1.0
 */
public class FirstFragment extends Fragment {

    FloatingActionButton fabPhone;
    TextView tv;
    View view;

    String phoneNumber="";
    String pesoGlobal="";
    String unitsGlobal="";

    Button button_insulin,button_registro,button_glucosa,button_glucosaCalcular;

    TextView textView_breafastCant, textView_lunchCant,textView_dinnerCant,textView_cantidadInsulina;

    EditText editText_insulinaInf,editText_glucosa,editText_cantidadAplicacionesInf;

    boolean editable=false;
    /**
     * Metodo constructor de a clase
     */
    public FirstFragment() {

    }

    /**
     * Constructor de la vista de la pantalla
     * @param inflater LayoutInflater Objeto que guarda los parametros de la vista
     * @param container ViewGroup contenedor de la pantalla
     * @param savedInstanceState Bundle Datos de los componentes
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        button_insulin = (Button) view.findViewById(R.id.button_Insulin);
        button_registro = (Button) view.findViewById(R.id.button_RegistroIn);
        button_glucosa = (Button) view.findViewById(R.id.button_GlucosaIn);

        button_glucosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.Iniciado)
                {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_glucosa, null);

                        editText_glucosa = (EditText) view.findViewById(R.id.editText_Glucosa);
                        textView_cantidadInsulina = (TextView) view.findViewById(R.id.textView_CantidadInsulina);
                        button_glucosaCalcular = (Button) view.findViewById(R.id.button_GlucosaCalcular);

                        button_glucosaCalcular.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String glucosaIndice = editText_glucosa.getText().toString();

                                if(!glucosaIndice.equals(""))
                                {
                                    String insulina = calcularInsuina(Integer.parseInt(glucosaIndice));
                                    textView_cantidadInsulina.setText(insulina);
                                    Calendar cal = Calendar.getInstance();
                                    if(Integer.parseInt(insulina)>0)
                                    {
                                        String date = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
                                        setRecordsINSERT(glucosaIndice, insulina, date);
                                    }
                                }
                            }
                        });

                        dialogBuilder.setView(view);
                        AlertDialog dialog = dialogBuilder.create();
                        dialog.show();
                }
                else
                {
                    Toast.makeText(v.getContext(), R.string.messageLogin, Toast.LENGTH_LONG).show();
                }
            }
        });

        button_insulin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.Iniciado)
                {
                    String peso = getUserinfo();
                    if(peso!=null)
                    {
                        if(!peso.equals(""))
                        {
                            int pesoUnidades = Integer.parseInt(peso.substring(0, peso.indexOf("'")));

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                            View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_insulin_info, null);

                            editText_insulinaInf = (EditText) view.findViewById(editText_InsulinaInf);
                            editText_cantidadAplicacionesInf = (EditText) view.findViewById(R.id.editText_CantidadAplicacionesInf);

                            editText_cantidadAplicacionesInf.setEnabled(false);
                            editText_insulinaInf.setEnabled(false);

                            textView_breafastCant = (TextView) view.findViewById(R.id.textView_BreakfastCant);
                            textView_dinnerCant = (TextView) view.findViewById(R.id.textView_DinnerCant);
                            textView_lunchCant = (TextView) view.findViewById(R.id.textView_LunchCant);

                            setTextEditText(String.valueOf(pesoUnidades * 0.55), "3", peso, editText_insulinaInf, editText_cantidadAplicacionesInf);

                            dialogBuilder.setView(view);
                            AlertDialog dialog = dialogBuilder.create();
                            dialog.show();
                        }
                        else
                        {
                            Toast.makeText(v.getContext(), R.string.NoUserInfo, Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), R.string.NoUserInfo, Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(v.getContext(), R.string.messageLogin, Toast.LENGTH_LONG).show();
                }
            }
        });
        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (MainActivity.Iniciado)
                {
                    ArrayList<String[]> recordsList = getRecords();

                    if (recordsList.size() > 0)
                    {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                        View view = getActivity().getLayoutInflater().inflate(R.layout.historial_dialog, null);
                        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
                        fillTable(tableLayout, view, recordsList);

                        dialogBuilder.setView(view);
                        AlertDialog dialog = dialogBuilder.create();
                        dialog.show();

                    }
                    else
                    {
                        Toast.makeText(v.getContext(), R.string.messageNoRecords, Toast.LENGTH_LONG).show();
                    }
                    recordsList = null;
                }
                else
                {
                    Toast.makeText(v.getContext(), R.string.messageLogin, Toast.LENGTH_LONG).show();
                }
            }
        });

        fabPhone = (FloatingActionButton)view.findViewById(R.id.fab_phone);
        fabPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), R.string.messageCall, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        fabPhone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + phoneNumber));


                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Por favor concede el permiso para llamar", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
                else
                {
                    if (phoneNumber != "")
                    {

                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getContext(), R.string.messageSetNumber, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        //Comprobar(MainActivity.Iniciado);
        setPhoneNumber(view);
        return view;
    }

    public void setRecordsINSERT(String glucose,String insulinAmount,String date)
    {
        boolean resultINSERT=false;
        try {
            resultINSERT = MainActivity.db.setRecordsINSERT(date,
                    glucose,
                    insulinAmount,
                    MainActivity.usuarioNombre);
        }
        catch (Exception e)
        {
        }
    }

    public String calcularInsuina (int indiceGlucosa)
    {
        int insulinaCantidad=(indiceGlucosa-120)/50;
        return String.valueOf(insulinaCantidad);
    }

    public void setTextEditText(String unidades, String recomendado, String peso, EditText editText_InsulinaInf, EditText editText_CantidadInf)
    {
        DecimalFormat df = new DecimalFormat("##.##");
        double pesoUnidades = Double.parseDouble(unidades);
        double Almuerzo = pesoUnidades*0.45;
        double Comida = pesoUnidades*0.20;
        double Cena = pesoUnidades*0.35;

        editText_CantidadInf.setText(recomendado);
        editText_InsulinaInf.setText(unidades+" mg");

        textView_lunchCant.setText(df.format(Comida)+" mg");
        textView_dinnerCant.setText(df.format(Cena)+" mg");
        textView_breafastCant.setText(df.format(Almuerzo)+" mg");
    }

    public void fillTable(TableLayout tl, View view, ArrayList<String[]> recordsData)
    {
                tl.setColumnStretchable(0, true);
                tl.setColumnStretchable(1, true);
                tl.setColumnStretchable(2, true);

                TableRow tr = new TableRow(view.getContext());

                tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                TextView tvFechaHora = new TextView(view.getContext());
                tvFechaHora.setText(R.string.columnNameDateHour);
                tvFechaHora.setTextColor(Color.BLUE);
                tvFechaHora.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tr.addView(tvFechaHora);

                TextView tvTipoDosis = new TextView(view.getContext());
                tvTipoDosis.setText(R.string.columnNameType);
                tvTipoDosis.setTextColor(Color.BLUE);
                tvTipoDosis.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tr.addView(tvTipoDosis);

                TextView tvDosis = new TextView(view.getContext());
                tvDosis.setText(R.string.columnNameAmount);
                tvDosis.setTextColor(Color.BLUE);
                tvDosis.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tr.addView(tvDosis);

                tl.addView(tr);

                for (int i=0;i<recordsData.size();i++)
                {
                    String[]recordsRow = recordsData.get(i);
                    tl.addView(setRows(recordsRow[0],recordsRow[1],recordsRow[2],view));
                }
    }

    public TableRow setRows(String dateHour, String type, String amount, View view)
    {
        TableRow tr = new TableRow(view.getContext());

        tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView tvFechaHora = new TextView(view.getContext());
        tvFechaHora.setText(dateHour);
        tvFechaHora.setTextColor(Color.BLACK);
        tvFechaHora.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tr.addView(tvFechaHora);

        TextView tvTipoDosis = new TextView(view.getContext());
        tvTipoDosis.setText(type);
        tvTipoDosis.setTextColor(Color.BLACK);
        tvTipoDosis.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tr.addView(tvTipoDosis);

        TextView tvDosis = new TextView(view.getContext());
        tvDosis.setText(amount);
        tvDosis.setTextColor(Color.BLACK);
        tvDosis.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tr.addView(tvDosis);
        return  tr;
    }

    public ArrayList<String[]> getRecords()
    {
        Cursor cursor =MainActivity.db.getRecordsSELECT(MainActivity.usuarioNombre);
        ArrayList<String[]> recordsData = new ArrayList<>();

        String[] recordsDataRow=null;

        while (cursor.moveToNext())
        {
                recordsDataRow=new String[3];
                recordsDataRow[0]=cursor.getString(1);
                recordsDataRow[1]=cursor.getString(2);
                recordsDataRow[2]=cursor.getString(3);

                recordsData.add(recordsDataRow);
        }
        return recordsData;
    }

    public String getUserinfo()
    {
        Cursor cursor =MainActivity.db.getUserinfoSELECT(MainActivity.usuarioNombre);

        String dataRow=null;

        while (cursor.moveToNext())
        {
            dataRow = cursor.getString(4);
        }
        return dataRow;
    }

    public void setPhoneNumber(View v)
    {
        Cursor cursor =db.getUserinfoSELECT(MainActivity.usuarioNombre);

        if(cursor.getCount()==0)
        {

        }
        else
        {
            while (cursor.moveToNext())
            {
                phoneNumber=cursor.getString(5);
            }
        }
    }

    /**
     * Metodo para activar la llamada
     */
    public void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    /**
     * Metodo para comprobar el tipo de usuario
     * @param usertype boolean Parametro del tipo de usuario
     */
    public void Comprobar(boolean usertype)
    {
        if(usertype){

        }
        else{

        }
    }
}

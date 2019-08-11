package com.developer.tonny.design.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.tonny.design.Activities.Medicamentos;
import com.developer.tonny.design.Activities.MainActivity;
import com.developer.tonny.design.R;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;
import static com.developer.tonny.design.Activities.MainActivity.db;


/**
 * Clase para manejo de pantallas
 * @author Alejandro
 * @version 1.0
 */
public class SecondFragment extends Fragment {

    ArrayList<String []> Datos = new ArrayList<>();
    String[]recogerDatos;
    ListView lv;
    TextView tvTitulo, tvHora, tvFecha;
    Button btnBorr;
    View view;
    TextView tv;
    int i=0;
    boolean act=false;
    FloatingActionButton fabAdd;

    /**
     * Metodo constructor de a clase
     */
    public SecondFragment() {

    }
    /**
     * Constructor de la vista de la pantalla
     * @param inflater LayoutInflater Objeto que guarda los parametros de la vista
     * @param container ViewGroup contenedor de la pantalla
     * @param savedInstanceState Bundle Datos de los componentes
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        view = inflater.inflate(R.layout.fragment_second, container, false);

        lv = (ListView) view.findViewById(R.id.listviewMedicamentos);

        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(view.getContext(), Medicamentos.class);
                startActivity(inte);
            }
        });
        tv = (TextView) view.findViewById(R.id.textView_MensajeSec);
        Comprobar(MainActivity.Iniciado);
        try {
            if (MainActivity.Iniciado) {
                Datos = getMedicineData(MainActivity.usuarioNombre);
                if (Datos != null) {
                    if(Datos.size()>0) {
                        CustomAdapter adapter = new CustomAdapter(view.getContext(), Datos);
                        lv.setAdapter(adapter);
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(view.getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public ArrayList<String []> getMedicineData(String user)
    {
        Cursor cursor =db.getMedicineSELECT(user);

        if(cursor.getCount()==0)
        {
            return null;
        }
        else
        {
            ArrayList <String []> data = new ArrayList<>();
            String [] dataFromRow;
            while (cursor.moveToNext())
            {
                dataFromRow = new String[5];
                dataFromRow[0]=cursor.getString(1);
                dataFromRow[1]=cursor.getString(2);
                dataFromRow[2]=cursor.getString(3);
                dataFromRow[3]=cursor.getString(4);
                dataFromRow[4]=cursor.getString(0);
                data.add(dataFromRow);
            }
            return data;
        }
    }

    /**
     * Metodo para comprobar el tipo de usuario
     * @param usertype boolean Parametro del tipo de usuario
     */
    public void Comprobar(boolean usertype)
    {
        if(!usertype){
            tv.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.GONE);
        }
        else{
            tv.setVisibility(View.GONE);
            fabAdd.setVisibility(View.VISIBLE);
        }
    }


}

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.tonny.design.Activities.Citas;
import com.developer.tonny.design.Activities.MainActivity;
import com.developer.tonny.design.R;

import java.util.ArrayList;

import static com.developer.tonny.design.Activities.MainActivity.db;

/**
 * Clase para manejo de pantallas
 * @author Alejandro
 * @version 1.0
 */
public class ThirdFragment extends Fragment {

    TextView tv;
    FloatingActionButton fabCreate;
    View view;
    ListView lv;
    ArrayList<String[]> Datos = new ArrayList<>();

    /**
     * Metodo constructor de a clase
     */
    public ThirdFragment() {

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
        view = inflater.inflate(R.layout.fragment_third, container, false);

        tv = (TextView) view.findViewById(R.id.textView_MensajeThird);
        lv = (ListView) view.findViewById(R.id.listviewCitas);

        fabCreate= (FloatingActionButton)view.findViewById(R.id.fap_create);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(view.getContext(), Citas.class);
                startActivity(inte);
            }
        });

        Comprobar(MainActivity.Iniciado);
        try {
            if (MainActivity.Iniciado) {
                Datos = getDatesData(MainActivity.usuarioNombre);
                if (Datos != null) {
                    if(Datos.size()>0) {
                        CustomAdapterCitas adapter = new CustomAdapterCitas(view.getContext(), Datos);
                        lv.setAdapter(adapter);
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(view.getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public ArrayList<String []> getDatesData(String user)
    {
        Cursor cursor =db.getDatesSELECT(user);

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
            fabCreate.setVisibility(View.GONE);
        }
        else{
            tv.setVisibility(View.GONE);
            fabCreate.setVisibility(View.VISIBLE);
        }
    }
}

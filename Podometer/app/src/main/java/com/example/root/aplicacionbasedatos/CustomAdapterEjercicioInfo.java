package com.example.root.aplicacionbasedatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.root.aplicacionbasedatos.R.id.textView_EiercicioTit;
import static com.example.root.aplicacionbasedatos.R.id.textView_ejercicioTit;

/**
 * Created by root on 12/07/17.
 */

public class CustomAdapterEjercicioInfo extends BaseAdapter{


    ArrayList<String[]> datos;
    String[]datosRecogidos;
    Context context;

    public CustomAdapterEjercicioInfo(Context context, ArrayList<String[]> datos) {
        // TODO Auto-generated constructor stub
        this.datos=datos;
        this.context=context;

    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_ejercicios, null);
        }
        datosRecogidos=datos.get(position);

        TextView textView_ejerciciotit = (TextView)view.findViewById(textView_EiercicioTit);
        textView_ejerciciotit.setText(datosRecogidos[0]);

        TextView textView_ejerciciodesc = (TextView) view.findViewById(R.id.textView_DescripcionEjercicio);
        textView_ejerciciodesc.setText(datosRecogidos[1]);
        datosRecogidos=null;

        return view;
    }

}

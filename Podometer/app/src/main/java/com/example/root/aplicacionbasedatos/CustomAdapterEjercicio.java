package com.example.root.aplicacionbasedatos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.root.aplicacionbasedatos.R.id.textView_ejercicioTit;

public class CustomAdapterEjercicio extends BaseAdapter {

    ArrayList<String[]> datos;
    ArrayList<Integer> images;
    String[]datosRecogidos;
    Context context;

    public CustomAdapterEjercicio(Context context, ArrayList<String[]> datos,ArrayList<Integer>images) {
        // TODO Auto-generated constructor stub
        this.datos=datos;
        this.images=images;
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
            view = inflater.inflate(R.layout.listview_ejerciciolayout, null);
        }
        datosRecogidos=datos.get(position);

        TextView textView_ejerciciotit = (TextView)view.findViewById(textView_ejercicioTit);
        textView_ejerciciotit.setText(datosRecogidos[0]);

        TextView textView_ejerciciodesc = (TextView) view.findViewById(R.id.textView_ejercicioDesc);
        textView_ejerciciodesc.setText(datosRecogidos[1]);

        ImageView imageView_Ejercicio = (ImageView) view.findViewById(R.id.imageView_ejercicio);
        imageView_Ejercicio.setImageResource(images.get(position));

        return view;
    }

}

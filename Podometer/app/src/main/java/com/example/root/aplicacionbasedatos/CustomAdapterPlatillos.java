package com.example.root.aplicacionbasedatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 11/07/17.
 */

public class CustomAdapterPlatillos extends BaseAdapter {

    ArrayList<String[]> datos;
    String[]datosRecogidos;
    Context context;

    public CustomAdapterPlatillos(Context context, ArrayList<String[]> datos) {
        // TODO Auto-generated constructor stub
        this.datos=datos;
        this.context=context;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_platillo, null);
        }
        datosRecogidos=datos.get(position);

        TextView textView_titulo = (TextView)view.findViewById(R.id.textView_Titulo);
        textView_titulo.setText(datosRecogidos[0]);

        TextView textView_texto = (TextView) view.findViewById(R.id.textView_Texto);
        textView_texto.setText(datosRecogidos[1]);
        datosRecogidos=null;
        return view;
    }
}

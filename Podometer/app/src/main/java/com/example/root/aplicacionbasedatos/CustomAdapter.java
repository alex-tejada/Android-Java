package com.example.root.aplicacionbasedatos;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 7/07/17.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<String[]> datos;
    ArrayList<Integer> images;
    String[]datosRecogidos;
    Context context;

    public CustomAdapter(Context context, ArrayList<String[]> datos,ArrayList<Integer>images) {
        // TODO Auto-generated constructor stub
        this.datos=datos;
        this.images=images;
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
            view = inflater.inflate(R.layout.listview_dietlayout, null);
        }
        datosRecogidos=datos.get(position);

        //Handle TextView and display string from your list
        TextView textView_nombre = (TextView)view.findViewById(R.id.textView_DietTi);
        textView_nombre.setText(datosRecogidos[0]);

        TextView textView_fecha = (TextView) view.findViewById(R.id.textView_Calorias);
        textView_fecha.setText(view.getResources().getString(R.string.dietCalorias)+datosRecogidos[1]);

        ImageView imageView_Foto = (ImageView) view.findViewById(R.id.imageView_DietTi);
        imageView_Foto.setImageResource(images.get(position));

        return view;
    }
}

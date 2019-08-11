package com.developer.tonny.design.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.developer.tonny.design.Activities.MainActivity;
import com.developer.tonny.design.Activities.Medicamentos;
import com.developer.tonny.design.R;

import java.util.ArrayList;


/**
 * Clase para generar los datos de la plantilla en XML
 * @author Alejandro
 * @version 1.0
 */

public class CustomAdapter extends BaseAdapter{

    ArrayList<String[]> datos;
    String[]datosRecogidos;
    Context context;


    /**
     * Metodo constructor para la clase CustomAdapter
     * @param context Context Contexto en donde se aplica el constructor
     * @param datos ArrayList Datos para mostrar en la plantilla
     */
    public CustomAdapter(Context context, ArrayList<String[]> datos) {
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

    /**
     * Metodo constructor para para la vista de la plantilla en XML
     * @param position int Posicion de la plantilla agregada
     * @param convertView View vista de la ventana
     * @param parent ViewGroup Contenedor de la ventana
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_layout_medicamentos, null);
        }
        datosRecogidos=datos.get(position);

        //Handle TextView and display string from your list
        TextView textView_nombre = (TextView)view.findViewById(R.id.textView_Nombre);
        textView_nombre.setText(datosRecogidos[0]);

        TextView textView_fecha = (TextView) view.findViewById(R.id.textView_Fecha);
        textView_fecha.setText(view.getResources().getString(R.string.listDias)+" "+datosRecogidos[1]);

        TextView textView_hora = (TextView) view.findViewById(R.id.textView_Hora);
        textView_hora.setText(view.getResources().getString(R.string.listCada)+" "+datosRecogidos[2]);

        TextView textView_Cantidad = (TextView) view.findViewById(R.id.textView_Cantidad);
        textView_Cantidad.setText(view.getResources().getString(R.string.listCantidad)+" "+datosRecogidos[3]);

        //Handle buttons and add onClickListeners
        Button button_eliminar = (Button)view.findViewById(R.id.button_Eliminar);
        Button button_editar = (Button) view.findViewById(R.id.button_Editar);

        button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                datosRecogidos=datos.get(position);

                Intent inte = new Intent(context, Medicamentos.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("name",datosRecogidos[0]);
                bundle.putString("date",datosRecogidos[1]);
                bundle.putString("hour",datosRecogidos[2]);
                bundle.putString("amount",datosRecogidos[3].substring(0,datosRecogidos[3].indexOf("m")));
                bundle.putString("amountUnit",datosRecogidos[3].substring(datosRecogidos[3].indexOf("m"),datosRecogidos[3].length()));
                bundle.putString("id",datosRecogidos[4]);

                inte.putExtras(bundle);
                context.startActivity(inte);

            }
        });

        button_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(v.getResources().getString(R.string.dialogMessage))
                        .setCancelable(false)
                        .setPositiveButton(v.getResources().getString(R.string.dialogYes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                String [] datosRecogidos = datos.get(position);
                                MainActivity.db.setMedicineDELETE(datosRecogidos[4]);
                                datos.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(v.getResources().getString(R.string.dialogNo), null)
                        .show();
            }
        });
        return view;
    }

}

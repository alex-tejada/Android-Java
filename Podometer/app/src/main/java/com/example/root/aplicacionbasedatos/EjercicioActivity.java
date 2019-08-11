package com.example.root.aplicacionbasedatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EjercicioActivity extends AppCompatActivity
{

    ListView lv;

    ArrayList<String[]> datos = new ArrayList<>();
    String [] datosRecogidos=new String[4];
    ArrayList<Integer> imagenes = new ArrayList<>();
    String descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        setTitle(R.string.ejercicioTitulo);

        lv = (ListView) findViewById(R.id.listView_Ejercicios);

        agregarDatos();
        CustomAdapterEjercicio adapter = new CustomAdapterEjercicio(getApplicationContext(),datos,imagenes);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                datosRecogidos = datos.get(position);
                Intent inte = new Intent(getApplicationContext(),EjercicioInfoActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("VideoLink",datosRecogidos[2]);
                bundle.putString("Titulo",datosRecogidos[0]);
                bundle.putString("exerciseKey",datosRecogidos[3]);
                datosRecogidos=null;
                inte.putExtras(bundle);
                startActivity(inte);

            }
        });
        datosRecogidos=null;
    }
    public void agregarDatos()
    {
        imagenes.add(R.drawable.exerciseabs);
        imagenes.add(R.drawable.exerciseback);
        imagenes.add(R.drawable.exercisebicep);
        imagenes.add(R.drawable.exercisechest);
        imagenes.add(R.drawable.exerciseglutes);
        imagenes.add(R.drawable.exerciselegs);
        imagenes.add(R.drawable.exercisetricep);


        datosRecogidos[0]=getString(R.string.ejercicioAbdominales);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"100-280";
        datosRecogidos[2]="1919eTCoESo";
        datosRecogidos[3]="abs";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioEspalda);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"170-560";
        datosRecogidos[2]="oUychjqfO8I";
        datosRecogidos[3]="back";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioBicep);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"170-290";
        datosRecogidos[2]="Ln-T6-XQYWs";
        datosRecogidos[3]="bicep";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioPecho);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"90-390";
        datosRecogidos[2]="Bi1IRzJIoAo";
        datosRecogidos[3]="chest";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioGluteos);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"130-490";
        datosRecogidos[2]="C8X96ItgyOg";
        datosRecogidos[3]="gluts";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioPiernas);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"190-350";
        datosRecogidos[2]="Womx4TM6p3A";
        datosRecogidos[3]="legs";
        datos.add(datosRecogidos);
        datosRecogidos=new String[4];
        datosRecogidos[0]=getString(R.string.ejercicioTricep);
        datosRecogidos[1]=getString(R.string.ejercicioCalorias)+"100-350";
        datosRecogidos[2]="NDw5dxst_ls";
        datosRecogidos[3]="tricep";
        datos.add(datosRecogidos);
    }

}

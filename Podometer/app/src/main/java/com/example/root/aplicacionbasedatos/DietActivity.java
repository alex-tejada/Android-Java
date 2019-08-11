package com.example.root.aplicacionbasedatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DietActivity extends AppCompatActivity {

    ArrayList<Integer>images = new ArrayList<>();
    ArrayList<String[]>datos = new ArrayList<>();
    String [] datosAgregados;//={"Ensalada de pollo","390","Ingredientes","Receta"};

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        setTitle(R.string.dietTitulo);

        lv= (ListView) findViewById(R.id.listView_FoodDiet);

        agregarInformacion();
        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),datos,images);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<Integer> imagen = new ArrayList<Integer>();
                datosAgregados = datos.get(position);
                imagen.add(images.get(position));
                Intent inte = new Intent(getApplicationContext(),DietaInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("imagenID",imagen);
                bundle.putString("Titulo",datosAgregados[0]);
                bundle.putString("dietKey",datosAgregados[2]);
                inte.putExtras(bundle);
                startActivity(inte);
            }
        });
    }
    public void agregarInformacion()
    {
        images.add(R.drawable.bbgburger);
        images.add(R.drawable.middleeastricesalad);
        images.add(R.drawable.whitebeanherbhummus);
        images.add(R.drawable.broccolifetaomelet);
        images.add(R.drawable.honeygrapefruit);

        datosAgregados = new String[3];
        datosAgregados[0]=getString(R.string.platoHamb);
        datosAgregados[1]="324";
        datosAgregados[2]="hamburguesa";
        datos.add(datosAgregados);

        datosAgregados = new String[3];
        datosAgregados[0]=getString(R.string.platoEns);
        datosAgregados[1]="380";
        datosAgregados[2]="ensalada";
        datos.add(datosAgregados);

        datosAgregados = new String[3];
        datosAgregados[0]=getString(R.string.platoHum);
        datosAgregados[1]="150";
        datosAgregados[2]="hummus";
        datos.add(datosAgregados);

        datosAgregados = new String[3];
        datosAgregados[0]=getString(R.string.platoBro);
        datosAgregados[1]="280";
        datosAgregados[2]="brocoli";
        datos.add(datosAgregados);

        datosAgregados = new String[3];
        datosAgregados[0]=getString(R.string.platoHon);
        datosAgregados[1]="470";
        datosAgregados[2]="honey";
        datos.add(datosAgregados);

        datosAgregados=null;
    }
}

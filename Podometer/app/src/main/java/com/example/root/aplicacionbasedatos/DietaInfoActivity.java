package com.example.root.aplicacionbasedatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Locale;

public class DietaInfoActivity extends AppCompatActivity {

    ImageView imageView_imagenPlatillo;
    TextView textView_titulo;
    ArrayList<String[]> datos = new ArrayList<>();
    String[]datosRecogidos;
    ListView lv;

    Conexion cn = new Conexion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta_info);
        setTitle(R.string.dietaInfoTitulo);

        //String Titulo = getIntent().getExtras().getString("Titulo");
        //Integer imagenID = getIntent().getIntegerArrayListExtra("imagenID").get(0);



        imageView_imagenPlatillo = (ImageView) findViewById(R.id.imageView_imagenPlatillo);
        textView_titulo = (TextView) findViewById(R.id.textView_titPlatillo);

        imageView_imagenPlatillo.setImageResource(getIntent().getIntegerArrayListExtra("imagenID").get(0));
        textView_titulo.setText(getIntent().getExtras().getString("Titulo"));

        lv = (ListView) findViewById(R.id.listView_platillos);

        orderXML(getIntent().getExtras().getString("dietKey"));
        CustomAdapterPlatillos adapter = new CustomAdapterPlatillos(getApplicationContext(),datos);
        lv.setAdapter(adapter);
        datos=null;
    }

    public void recopilarDatos(String[] dietInfo)
    {
        datosRecogidos = new String[2];
        datosRecogidos[0]=getString(R.string.dietaInfoIngredientes);
        datosRecogidos[1] = dietInfo[1];
        datos.add(datosRecogidos);

        datosRecogidos = new String[2];
        datosRecogidos[0] = getString(R.string.dietaInfoReceta);
        datosRecogidos[1] = dietInfo[0];
        datos.add(datosRecogidos);

        datosRecogidos=null;
    }

    public void orderXML(String dietKey)
    {
        String recipe="recipe", ingrdients="ingrdients";
        Document doc= cn.getXML("http://alexurie.x10host.com/diet.xml");

        if(doc==null)
        {
            Toast.makeText(getApplication(),"null",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!Locale.getDefault().getLanguage().equals("en"))
            {
                recipe+="ES";
                ingrdients+="ES";
            }
            String [] dietValues= new String[2];

            Element rootElement= doc.getDocumentElement();

            NodeList nodeList = rootElement.getElementsByTagName(dietKey);
            Node node = nodeList.item(0);

            NodeList nodeChildrenList=node.getChildNodes();

            for (int i=0;i<nodeChildrenList.getLength();i++)
            {
                Node nodeChild = nodeChildrenList.item(i);
                if(nodeChild.getNodeName().equals(recipe))
                {
                    dietValues[0]=nodeChild.getTextContent();
                }
                else if(nodeChild.getNodeName().equals(ingrdients))
                {
                    dietValues[1]=nodeChild.getTextContent();
                    break;
                }
            }
            recopilarDatos(dietValues);
        }
    }
}

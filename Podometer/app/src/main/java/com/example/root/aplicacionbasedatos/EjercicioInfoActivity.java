package com.example.root.aplicacionbasedatos;

import android.icu.util.RangeValueIterator;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.R.attr.name;
import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.view.View.X;

public class EjercicioInfoActivity extends YouTubeBaseActivity {

    YouTubePlayerView youtube;
    YouTubePlayer.OnInitializedListener youtubelistener;
    ListView lv;
    TextView textView_titEjercicios;
    ArrayList<String[]> datos = new ArrayList<>();
    String [] datosRecogidos=new String[2];
    public static final String API_KEY="AIzaSyBK9H7RJ_PHH-1LdN9Ceyx7V8g1QxaASiw";
    Conexion cn = new Conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_info);
        setTitle(R.string.ejercicioInfoTitulo);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        youtube = (YouTubePlayerView) findViewById(R.id.youtubeplayer);
        lv = (ListView) findViewById(R.id.listview_ejerciciosInfo);
        textView_titEjercicios = (TextView) findViewById(R.id.textView_titEjercicios);
        textView_titEjercicios.setText(getIntent().getExtras().getString("Titulo"));
        youtubelistener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                youTubePlayer.loadVideo(getIntent().getExtras().getString("VideoLink"));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        orderXML(getIntent().getExtras().getString("exerciseKey"),"routineDays","routineTime");
        CustomAdapterEjercicioInfo adapter = new CustomAdapterEjercicioInfo(getApplicationContext(),datos);
        lv.setAdapter(adapter);
        youtube.initialize(API_KEY,youtubelistener);
        datos=null;
    }

    public void recopilarDatos(String[] text)
    {

        datosRecogidos[0]=getString(R.string.ejercicioInfoDias);
        datosRecogidos[1]=text[0];

        datos.add(datosRecogidos);

        datosRecogidos = new String[2];
        datosRecogidos[0]=getString(R.string.ejercicioInfoTiempo);
        datosRecogidos[1]=text[1];

        datos.add(datosRecogidos);

        datosRecogidos=null;
    }

    public void orderXML(String exerciseKey,String routineDays,String routineTime)
    {
        Document doc= cn.getXML("http://alexurie.x10host.com/exercise.xml");

        if(doc==null)
        {
            Toast.makeText(getApplication(),"null",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!Locale.getDefault().getLanguage().equals("en"))
            {
                routineDays+="ES";
                routineTime+="ES";
            }
            String [] routineValues= new String[2];

            Element rootElement= doc.getDocumentElement();

            NodeList nodeList = rootElement.getElementsByTagName(exerciseKey);
            Node node = nodeList.item(0);

            NodeList nodeChildrenList=node.getChildNodes();

            for (int i=0;i<nodeChildrenList.getLength();i++)
            {
                Node nodeChild = nodeChildrenList.item(i);
                if(nodeChild.getNodeName().equals(routineDays))
                {
                    routineValues[0]=nodeChild.getTextContent();
                }
                else if(nodeChild.getNodeName().equals(routineTime))
                {
                    routineValues[1]=nodeChild.getTextContent();
                    break;
                }
            }
            recopilarDatos(routineValues);
        }
    }

}

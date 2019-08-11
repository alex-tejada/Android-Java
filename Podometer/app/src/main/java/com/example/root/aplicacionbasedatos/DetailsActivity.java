package com.example.root.aplicacionbasedatos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ListView lv;
    TextView textView_account;
    Button button_eraseRecords;

    ArrayList<String> recordsList = new ArrayList<>();
    ArrayAdapter adapter;
    String username="";

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(R.string.detailTit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        lv = (ListView) findViewById(R.id.listView_Records);
        textView_account= (TextView) findViewById(R.id.textView_Account);
        button_eraseRecords = (Button) findViewById(R.id.button_EraseRecords);
        button_eraseRecords.setVisibility(View.INVISIBLE);

        username = getIntent().getExtras().getString("username");
        String userEncrypt =encriptar.encrypt(username);
        recordsSELECT(userEncrypt);
        textView_account.setText(getString(R.string.detailsAccountName)+username);

        button_eraseRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String userEncrypt =encriptar.encrypt(username);
                recordsDELETE(userEncrypt);
                    recordsList.clear();
                    adapter.notifyDataSetChanged();
                    button_eraseRecords.setEnabled(false);
            }
        });
    }

    public void recordsSELECT(String user) {

        String responseString= cn.recordsSELECT("http://alexurie.x10host.com/SELECTrecordsSC.php",user);

        if(responseString.contains("[")) {
            try {
                JSONArray jsonArray = new JSONArray(responseString);
                JSONObject jsonObject;

                int j = jsonArray.length()-1;
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.getJSONObject(j);

                    recordsList.add("\n"+getString(R.string.detailFecha) +" "+ encriptar.decrypt(jsonObject.getString("fecha")) +
                            "\n"+getString(R.string.detailCantidadPasos) +" "+ encriptar.decrypt(jsonObject.getString("cantidadPasos")) +
                            "\n"+getString(R.string.detailCantidadCalorias) +" "+ encriptar.decrypt(jsonObject.getString("cantidadCalorias")) +
                            "\n"+getString(R.string.detailCantidadRecorrida) +" "+ encriptar.decrypt(jsonObject.getString("cantidadRecorrida"))+"\n");
                    j-=1;
                }
                if(recordsList.size()>0)
                {
                  setAdapterListView();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
        }
    }

    public void setAdapterListView()
    {
        if(adapter==null)
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,recordsList);
            lv.setAdapter(adapter);
            button_eraseRecords.setVisibility(View.VISIBLE);
        }
    }
    public void recordsDELETE(String user)
    {
        String responseString= cn.recordsSELECT("http://alexurie.x10host.com/DELETErecordsSC.php",user);
        Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
    }
}

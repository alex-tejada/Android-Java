package com.example.root.aplicacionbasedatos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editText_US,editText_PASS;
    Button button_login,button_register,button_invitado;

    String nombreUsuario;
    boolean usurioExiste;
    public static boolean usuarioLogueado=false;

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.loginTituloVentana);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editText_US= (EditText) findViewById(R.id.editText_Username);
        editText_PASS = (EditText) findViewById(R.id.editText_Password);

        button_login = (Button) findViewById(R.id.button_Login);
        button_register = (Button) findViewById(R.id.button_Register);
        button_invitado = (Button) findViewById(R.id.button_Invitado);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!editText_PASS.getText().toString().equals("")&&!editText_US.getText().toString().equals(""))
                {
                    String userEncrypt = encriptar.sha1Encryption(editText_US.getText().toString());
                    String passEncrypt = encriptar.sha1Encryption(editText_PASS.getText().toString());
                    checkUser(userEncrypt,passEncrypt);
                }
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(inte);
            }
        });

        button_invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                usuarioLogueado=false;
                Intent inte = new Intent(getApplicationContext(), PasosActivity.class);
                startActivity(inte);
            }
        });

    }

    public void checkUser (String user, String pass)
    {
        /*String responseString = cn.loginSELECT("http://alexurie.x10host.com/SELECTloginSC.php", user,pass);

        if(responseString.equals("1"))
        {
            usuarioLogueado=true;*/
            Intent inte = new Intent(getApplicationContext(), PasosActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username",editText_US.getText().toString());
            inte.putExtras(bundle);
            startActivity(inte);
            /*editText_PASS.setText("");
            editText_US.setText("");
            MainActivity.this.finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
        }
        */
    }


}

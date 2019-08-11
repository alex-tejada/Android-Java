package com.developer.tonny.design.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.tonny.design.Conexion;
import com.developer.tonny.design.Encriptar;
import com.developer.tonny.design.R;

import java.util.ArrayList;

/**
 * Clase para abrir la ventana de Registro
 * @author Alejandro
 * @version 1.0
 */
public class registroActivity extends AppCompatActivity {

    EditText txtContr2, txtEmail, txtConfContr;
    Button btnRegme;

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();
    /**
     * Constructor de clase
     * @param savedInstanceState Bundle Datos de componentes de ventana
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnRegme = (Button) findViewById(R.id.btnRegistrarme);

        txtEmail = (EditText) findViewById(R.id.txtCorreo);
        txtContr2 = (EditText) findViewById(R.id.txtContrasena2);
        txtConfContr = (EditText) findViewById(R.id.txtConfContrasena);

        setToolbar();

        btnRegme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mailEncrypt = encriptar.sha1Encryption(txtEmail.getText().toString());
                String passEncrypt = encriptar.sha1Encryption(txtConfContr.getText().toString());

                registrar(mailEncrypt,passEncrypt);
            }
        });

    }

    /**
     * Metodo para registrar un usuario
     */
    public void registrar(String user, String pass)
    {
               if(!txtEmail.getText().toString().equals("")&&
                  !txtConfContr.getText().toString().equals("")&&
                  !txtContr2.getText().toString().equals("")) {

                   if (txtConfContr.getText().toString().equals(txtContr2.getText().toString())) {

                       if (txtEmail.getText().toString().contains("@") &&
                               txtEmail.getText().toString().contains(".com"))
                       {

                           String responseString = cn.loginSELECT("http://alexurie.x10host.com/INSERTusersSC.php", user,pass);
                           Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();

                       }
                       else {
                           Toast.makeText(getApplicationContext(), R.string.errorEmail, Toast.LENGTH_SHORT).show();
                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(), R.string.errorPasswordNotEquals, Toast.LENGTH_SHORT).show();
                   }
               }
               else{
                   Toast.makeText(getApplicationContext(), R.string.errorFillSpaces, Toast.LENGTH_SHORT).show();
               }
    }

    /**
     * Metodo para agregar barra de herramientas
     */
    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

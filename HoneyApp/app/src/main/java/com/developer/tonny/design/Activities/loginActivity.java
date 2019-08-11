package com.developer.tonny.design.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
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

import static android.media.CamcorderProfile.get;
import static com.developer.tonny.design.Activities.MainActivity.db;

/**
 * Clase para abrir la ventana de Inicio de sesion
 * @author Alejandro
 * @version 1.0
 */
public class loginActivity extends AppCompatActivity {

    Button btnEnt,btnReg;
    EditText txtUs, txtContr1;

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    /**
     * Constructor de clase
     * @param savedInstanceState Bundle Datos de componentes de ventana
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("My Honey App");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnEnt = (Button) findViewById(R.id.btnEntrar);
        btnReg = (Button) findViewById(R.id.btnRegistrarse);


        txtUs = (EditText) findViewById(R.id.txtUsuario);
        txtContr1 = (EditText) findViewById(R.id.txtContrasena1);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(),registroActivity.class);
                startActivity(inte);
            }
        });
        btnEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtUs.getText().toString().equals("")&&!txtContr1.getText().toString().equals(""))
                {

                    String userEncrypt = encriptar.sha1Encryption(txtUs.getText().toString());
                    String passEncrypt = encriptar.sha1Encryption(txtContr1.getText().toString());

                   checkUser(userEncrypt,passEncrypt);
                }
            }
        });

        setToolbar();
    }

    /**
     * Metodo para comprobar si el usuario esta reggistrado
     */

    /**
     * Metodo para agregar barra de herramientas
     */
    public void checkUser (String user, String pass)
    {
        String responseString = cn.loginSELECT("http://alexurie.x10host.com/SELECTloginSC.php", user,pass);

        if(responseString.equals("1"))
        {
            MainActivity.Iniciado=true;
            MainActivity.usuarioNombre=txtUs.getText().toString();
            getUser();
            setRegistry();
            Intent inte = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(inte);
            loginActivity.this.finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_SHORT).show();
        }
    }

    public void getUser()
    {
      Cursor cursor = MainActivity.db.getUserinfoSELECT(MainActivity.usuarioNombre);

        if(cursor.getCount()==0)
        {
            boolean resultINSERT=MainActivity.db.setUserinfoINSERT("","","","","","",MainActivity.usuarioNombre);

            /*if(resultINSERT)
            {
                Toast.makeText(getApplicationContext(), "Nuevo", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public void setRegistry()
    {
        boolean resultINSERT=false;
        try {
            resultINSERT = MainActivity.db.setRegistryINSERT(MainActivity.usuarioNombre);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        /*if(resultINSERT)
        {
            Toast.makeText(getApplicationContext(),"Registro editdo",Toast.LENGTH_LONG).show();
        }*/
    }

    private void setToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

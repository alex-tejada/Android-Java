package com.example.root.aplicacionbasedatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText editText_us,editText_pass;
    Button button_registerReg;

    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.registerTituloVentana);

        editText_us = (EditText) findViewById(R.id.editText_UsernameReg);
        editText_pass = (EditText) findViewById(R.id.editText_PasswordReg);

        button_registerReg = (Button) findViewById(R.id.button_RegisterReg);

        button_registerReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!editText_pass.getText().toString().equals("")&&!editText_us.getText().toString().equals(""))
                {
                    String userEncrypt = encriptar.sha1Encryption(editText_us.getText().toString());
                    String passEncrypt = encriptar.sha1Encryption(editText_pass.getText().toString());
                    usersINSERT(userEncrypt,passEncrypt);
                }
            }
        });
    }

    public void usersINSERT(String user,String pass)
    {
        String responseString = cn.loginSELECT("http://alexurie.x10host.com/INSERTusersSC.php", user,pass);

        Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
    }

}

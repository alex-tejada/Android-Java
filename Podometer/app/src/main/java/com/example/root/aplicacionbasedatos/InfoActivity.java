package com.example.root.aplicacionbasedatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    Button button_changePassword,button_records;
    EditText editText_changePassword;
    String username="";
    Conexion cn = new Conexion();
    Encriptar encriptar = new Encriptar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle(R.string.infoTit);

        button_changePassword = (Button) findViewById(R.id.button_PasswordChange);
        button_records = (Button) findViewById(R.id.button_Records);

        username = getIntent().getExtras().getString("username");

        editText_changePassword = (EditText) findViewById(R.id.editText_PasswordChange);

        button_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if(!editText_changePassword.getText().toString().equals(""))
               {
                   String userEncrypt =encriptar.encrypt(username);
                   String passEncrypt = encriptar.sha1Encryption(editText_changePassword.getText().toString());

                  userInfoUPDATE(userEncrypt,passEncrypt);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"Ingrese la nueva contraseña porfavor",Toast.LENGTH_LONG).show();
               }
            }
        });

        button_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(getApplicationContext(),DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                inte.putExtras(bundle);
                startActivity(inte);
            }
        });
    }

    public void userInfoUPDATE(String user, String pass)
    {
        String responseString = cn.userInfoUPDATE("http://alexurie.x10host.com/UPDATEuserInfoSC.php",user,pass);
        Toast.makeText(getApplicationContext(),responseString,Toast.LENGTH_LONG).show();
    }
}

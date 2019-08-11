package com.developer.tonny.design.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.developer.tonny.design.R;

import java.util.Locale;

public class logoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        CerrarSesion();
    }

    public void CerrarSesion(){

        if(ComprobarIdioma())
        {
            Toast.makeText(getApplicationContext(), "Sesion Cerrada", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
        }
        MainActivity.Iniciado=false;
        logout();
        MainActivity.usuarioNombre="";
        Intent logout = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(logout);
        logoutActivity.this.finish();
    }

    public void logout()
    {
        long result;

        result=MainActivity.db.setRegistryDELETE();

        /*if(result==1)
        {
            Toast.makeText(getApplicationContext(), "Registry DELETE", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No DELETE",Toast.LENGTH_SHORT).show();
        }*/
    }
    public boolean ComprobarIdioma(){
        String Lang = Locale.getDefault().getLanguage();

        if(Lang.equals("en"))
        {
            return false;
        }
        else{
            return true;
        }
    }
}

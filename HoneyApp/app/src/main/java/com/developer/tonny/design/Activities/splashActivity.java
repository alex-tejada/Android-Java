package com.developer.tonny.design.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.developer.tonny.design.Database;
import com.developer.tonny.design.R;

import static com.developer.tonny.design.Activities.MainActivity.db;

public class splashActivity extends AppCompatActivity {

    CountDownTimer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Inicializar Timer
        count = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
            }

            @Override
            public void onFinish()
            {
                Intent inte = new Intent(splashActivity.this,MainActivity.class);
                startActivity(inte);
                splashActivity.this.finish();
            }
        }.start();
    }


}

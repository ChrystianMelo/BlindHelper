package com.coltec.cfgs.blindhelper;

/**
 * Developed by Chrystian Melo
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static String macSelected = "BlindHelper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.macSelected, 0);

        Button btn_bluetooth = findViewById(R.id.btn_bluetooth);
        if(!pref.getString("Mac_Address","").isEmpty()){//there is mac selected yet
            btn_bluetooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bluetooth = new Intent(MainActivity.this, Bluetooth.class);

                    // cria o bundle e o insere na nova Intent
                    Bundle args = new Bundle();
                    args.putString("Mac_Address", pref.getString("Mac_Address",""));

                    bluetooth.putExtras(args);

                    startActivity(bluetooth);//bluetooth tela
                    finish();
                }
            });
        }else{//there is no mac selected yet
            btn_bluetooth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bluetooth2 = new Intent(MainActivity.this, Bluetooth2.class);
                    startActivity(bluetooth2);//bluetooth tela
                    finish();
                }
            });
        }


    }
}

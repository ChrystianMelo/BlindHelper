package com.coltec.cfgs.blindhelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Developed by Chrystian Melo
 */

public class Bluetooth2 extends Activity {

    public static TextView statusMessage;
    public static TextView output_text;
    public static EditText mac_add;//samuel's phone"D4:63:C6:86:22:D1"
    private static String macSelected = "BlindHelper";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth2);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences(Bluetooth2.macSelected, 0);

        statusMessage = findViewById(R.id.lbl_status);
        output_text = findViewById(R.id.output_text);
        mac_add = (EditText)findViewById(R.id.mac);


        Button btn_bluetooth = findViewById(R.id.ok);
        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bluetooth2.this, Bluetooth.class);

                String str = mac_add.getText().toString();

                // cria o bundle e o insere na nova Intent
                Bundle args = new Bundle();
                args.putString("Mac_Address", str);

                intent.putExtras(args);

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Mac_Address", str);
                editor.commit();

                startActivity(intent);//user tela
                finish();

            }
        });

    }

}
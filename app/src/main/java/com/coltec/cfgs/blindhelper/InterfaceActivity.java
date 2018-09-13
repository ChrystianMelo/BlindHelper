package com.coltec.cfgs.blindhelper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class InterfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        // recupera dado do bundle
        Bundle activityBundle = this.getIntent().getExtras();
        String name = activityBundle.getString("Nome");

        // imprime dado na tela
        TextView lblMessage;
        lblMessage = findViewById(R.id.text_Welcome);
        lblMessage.setText( "Welcome, ");
        lblMessage = findViewById(R.id.text_Name);
        lblMessage.setText(name + " !!");



        Button btn_bluetooth = findViewById(R.id.btn_bluetooth);
        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bluetooth = new Intent(InterfaceActivity.this, Bluetooth.class);
                startActivity(bluetooth);//bluetooth tela
            }
        });


    }
}

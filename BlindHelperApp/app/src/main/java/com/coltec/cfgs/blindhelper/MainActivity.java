package com.coltec.cfgs.blindhelper;

/**
 * Created by Chrystian
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText user = findViewById(R.id.txt_user);
        final EditText pass = findViewById(R.id.txt_pass);
        Button btn_Sign = findViewById(R.id.btn_sign);

        btn_Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterfaceActivity.class);
                // cria o bundle e o insere na nova Intent
                Bundle args = new Bundle();
                args.putString("Nome", user.getText().toString());
                EditText edittext = (EditText)findViewById(R.id.txt_pass);
                edittext.setTransformationMethod(new AsteriskPasswordTransformationMethod());

                intent.putExtras(args);

                startActivity(intent);//login tela
            }
        });


    }
}

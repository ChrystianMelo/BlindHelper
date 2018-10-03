package com.coltec.cfgs.blindhelper;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

/**
 *  Developed by Chrystian Melo
 */

public class Bluetooth extends AppCompatActivity {

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;
    public static TextView statusMessage;
    public BluetoothConnection connection;
    public static TextView output_text;
    public static Context mContext;
    public static  String output_text_string = "";

    public static Handler handler = new Handler() {
        String msn = "nada";

        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            if(dataString.equals("---N"))
                statusMessage.setText("Ocorreu um erro durante a conexão D:");
            else if(dataString.equals("---S"))
                statusMessage.setText("Funcionando perfeitamente");
            else {
                output_text.setMovementMethod(new ScrollingMovementMethod());
                output_text_string += "ele: " + dataString + "\n";
                output_text.setText(output_text_string);
                this.msn = dataString;
                Bluetooth.verify(this.msn);//a cada msn confiro se é oq eu espero
            }
        }
    };

    public static void verify(String x){
        if (x.equals("0")) //obstaculo à esquerda
            MediaPlayer.create(mContext, R.raw.ob_esq).start();
        else if(x.equals("1"))//obstaculo à direira
            MediaPlayer.create(mContext, R.raw.ob_dir).start();
        else if(x.equals("2"))//obstaculo frente
            MediaPlayer.create(mContext, R.raw.ob_fren).start();
        else if(x.equals("3"))//obstaculo abaixo
            MediaPlayer.create(mContext, R.raw.ob_abai).start();
        else if(x.equals("4"))//vire à Esq
            MediaPlayer.create(mContext, R.raw.vir_esq).start();
        else if(x.equals("5")) //vire à Dir
            MediaPlayer.create(mContext, R.raw.vir_dir).start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mContext = getApplicationContext();
        statusMessage = findViewById(R.id.lbl_status);
        output_text = findViewById(R.id.output_text);

        //String macSelected = "D4:63:C6:86:22:D1";//samuel's phone
        //connectAsClient(macSelected);

        // recupera dado do bundle
        Bundle activityBundle = this.getIntent().getExtras();
        String st = activityBundle.getString("Mac_Address","");
        connectAsClient(st);// connecting bluetooth

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando...TENTE REINICIAR O APP :(");
            return;
        } else {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        if(!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
            statusMessage.setText("Ativando Bluetooth...");
        } else {
            statusMessage.setText("Bluetooth está ativado :)");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ENABLE_BLUETOOTH) {
            if(resultCode == RESULT_OK) {
                statusMessage.setText("Bluetooth ativado");
            }
            else {
                statusMessage.setText("Falha ao ativar bluetooth");
            }
        }
        if(requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {
            if(resultCode == RESULT_OK) {
                statusMessage.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));
                //macSelected = data.getStringExtra("btDevAddress");
            }
            else {
                statusMessage.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }

    public void connectAsClient(String macSelected) {
        System.out.println(macSelected);
        connection = new BluetoothConnection(macSelected);
        connection.start();
    }

}
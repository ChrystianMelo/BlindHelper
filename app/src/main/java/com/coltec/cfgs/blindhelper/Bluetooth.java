package com.coltec.cfgs.blindhelper;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bluetooth extends AppCompatActivity {

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;
    public static TextView statusMessage;
    public BluetoothConnection connection;
    public Button btn_paired;
    public Button btn_search;
    public Button btn_visibility;
    public Button btn_connect_client;
    public Button btn_connect_server;
    public static TextView output_text;
    public static  String output_text_string = "";
    public EditText input_text;
    public String macSelected = "";

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString= new String(data);

            if(dataString.equals("---N"))
                statusMessage.setText("Ocorreu um erro durante a conexão D:");
            else if(dataString.equals("---S"))
                statusMessage.setText("Conectado :D");
            else {
                output_text_string += "ele: " + dataString + "\n";
                output_text.setText(output_text_string);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        statusMessage = findViewById(R.id.lbl_status);
        btn_paired = findViewById(R.id.btn_paired);
        btn_search = findViewById(R.id.btn_search);
        btn_visibility = findViewById(R.id.btn_visibility);
        btn_connect_client = findViewById(R.id.btn_connect_client);
        btn_connect_server = findViewById(R.id.btn_connect_server);

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
            return;
        } else {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        if(!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
            statusMessage.setText("Solicitando ativação do Bluetooth...");
        } else {
            statusMessage.setText("Bluetooth está pronto para ser usado :)");
        }

        btn_paired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPairedDevices();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverDevices();
            }
        });

        btn_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableVisibility();
            }
        });

        btn_connect_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectAsClient();
            }
        });

        btn_connect_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectAsServer();
            }
        });

        //sendMessage();
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
                macSelected = data.getStringExtra("btDevAddress");
            }
            else {
                statusMessage.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }

    public void searchPairedDevices() {
        Intent searchPairedDevicesIntent = new Intent(this, BluetoothPairedDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

    public void discoverDevices() {
        Intent searchPairedDevicesIntent = new Intent(this, BluetoothDiscoveredDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
    }

    public void enableVisibility() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        startActivity(discoverableIntent);
    }

    public void connectAsClient() {
        System.out.println(macSelected);
        connection = new BluetoothConnection(macSelected);
        connection.start();
    }

    public void connectAsServer() {
        connection = new BluetoothConnection();
        connection.start();
    }

    /*public void sendMessage() {
        leitura
        if (leitura == 1)//esq
            MediaPlayer ring= MediaPlayer.create(InterfaceActivity.this,R.raw.left);
        else if (leitura == 2)//esq+encima
            MediaPlayer ring= MediaPlayer.create(InterfaceActivity.this,R.raw.leftup);
        else if (leitura == 3)
            .
            .
            .
        else
            MediaPlayer ring= MediaPlayer.create(InterfaceActivity.this,R.raw.error);
        ring.start();

    }*/
}
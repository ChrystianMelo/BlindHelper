package com.coltec.cfgs.blindhelper;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



/**
 * Developed by Chrystian Melo
 */

public class Bluetooth2 extends Activity {


    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_DISCOVERED_DEVICE = 3;
    public static TextView statusMessage;
    public static TextView output_text;//samuel's phone"D4:63:C6:86:22:D1"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth2);

        statusMessage = findViewById(R.id.lbl_status);
        output_text = findViewById(R.id.output_text);

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

        Button btn_Vai = findViewById(R.id.ok);
        btn_Vai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverDevices();
            }});

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
    }

    public void discoverDevices() {
        Intent searchPairedDevicesIntent = new Intent(Bluetooth2.this, BluetoothDiscoveredDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
    }

}
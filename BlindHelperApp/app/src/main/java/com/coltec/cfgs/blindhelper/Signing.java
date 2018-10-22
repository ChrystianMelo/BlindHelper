package com.coltec.cfgs.blindhelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Developed by Chrystian Melo
 */

public class Signing extends Activity {

    private DBHelper userDB;

    TextView name;
    TextView email;
    TextView height;
    int id_To_Update = 0;
    boolean first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);
        name = (TextView) findViewById(R.id.editTextName);
        email = (TextView) findViewById(R.id.editTextEmail);
        height = (TextView) findViewById(R.id.editTextHeight);

        userDB = new DBHelper(this);

        first = userDB.numberOfRows()> 0;

        if(!first) {
            //means this is the view part not the add contact part.
            Cursor rs = userDB.getData(0);
            id_To_Update = 0;
            rs.moveToFirst();

            String nam = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_NAME));
            String emai = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_EMAIL));
            String heigh = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_HEIGHT));

            if (!rs.isClosed())  {
                rs.close();
            }
            Button b = (Button)findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);

            name.setText((CharSequence)nam);
            name.setFocusable(false);
            name.setClickable(false);

            email.setText((CharSequence)emai);
            email.setFocusable(false);
            email.setClickable(false);

            height.setText((CharSequence)heigh);
            height.setFocusable(false);
            height.setClickable(false);
        }
    }
}
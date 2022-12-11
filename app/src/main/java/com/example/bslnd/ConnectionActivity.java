package com.example.bslnd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectionActivity extends AppCompatActivity {

    Button connect;
    EditText ipAddress, port;
    String ipAddressHolder, portHolder;
    boolean editTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_activity);
        connect = (Button) findViewById(R.id.connect);
        ipAddress = (EditText) findViewById(R.id.ipAddress);
        port = (EditText) findViewById(R.id.fatherName);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkEditTextStatus();
                checkFinalResult();
            }
        });
    }

    public void checkEditTextStatus() {
        ipAddressHolder = ipAddress.getText().toString();
        portHolder = port.getText().toString();
        if (TextUtils.isEmpty(ipAddressHolder) || TextUtils.isEmpty(portHolder)) {
            editTextEmptyHolder = false;
        } else {
            editTextEmptyHolder = true;
        }
    }

    public void checkFinalResult() {
        if(editTextEmptyHolder) {
            Toast.makeText(ConnectionActivity.this, "Connection Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConnectionActivity.this, LoginActivity.class);
            //Intent intent = new Intent(ConnectionActivity.this, RegisterActivity.class);
            intent.putExtra("ipAddress", ipAddressHolder);
            intent.putExtra("port", portHolder);
            startActivity(intent);
        } else {
            Toast.makeText(ConnectionActivity.this, "Enter ipAddress and Port", Toast.LENGTH_SHORT).show();
        }
    }
}
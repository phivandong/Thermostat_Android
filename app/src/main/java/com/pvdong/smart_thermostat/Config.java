package com.pvdong.smart_thermostat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {

    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Option");
        setContentView(R.layout.activity_config);
        super.onCreate(savedInstanceState);

        tvText = findViewById(R.id.tvText);

        tvText.setText("MQTT");
    }
}

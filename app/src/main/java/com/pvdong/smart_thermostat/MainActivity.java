package com.pvdong.smart_thermostat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageButton ibConfig;
    ImageView ivRoom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibConfig = (ImageButton) findViewById(R.id.ibConfig);
        ivRoom1 = (ImageView) findViewById(R.id.ivRoom1);

        ibConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Config.class));
            }
        });

        ivRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RoomOption.class));
            }
        });
    }
}
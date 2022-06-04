package com.pvdong.smart_thermostat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Timer extends AppCompatActivity {
    private FloatingActionButton addTimerBtn;
    private Toolbar toolbar;
    ImageView ivTimer1;

    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Option");
        setContentView(R.layout.activity_timer);
        super.onCreate(savedInstanceState);

        ivTimer1 = findViewById(R.id.ivTimer1);

        ivTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Timer.this, PickTime.class));
                //replaceFragment(new PickTimeFragment());
            }
        });
    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();
//    }
}

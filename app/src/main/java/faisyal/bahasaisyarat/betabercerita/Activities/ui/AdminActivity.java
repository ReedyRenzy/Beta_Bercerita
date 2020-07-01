package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import faisyal.bahasaisyarat.betabercerita.R;


public class AdminActivity extends AppCompatActivity {

    private ImageView Up_Video,Upload_Banner,calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        Up_Video = findViewById(R.id.Upload_Video);
        Upload_Banner = findViewById(R.id.Upload_Banner);
        calender = findViewById(R.id.calender);


        Up_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(),UploadVideoActivity.class);
                startActivity(loginActivity);
                finish();


            }
        });


            BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Nav);
            bottomNavigationView.setSelectedItemId(R.id.upload_bottom);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.upload_bottom:
                            return true;
                        case R.id.settings_bottom:
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.realtimechat_buttom:
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return false;
                }
            });

    }
}
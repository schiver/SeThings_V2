package com.example.schiver.sethings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.label_sethings);
        iv = (ImageView) findViewById(R.id.logo_sethings);
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myAnimation);
        iv.startAnimation(myAnimation);
        final Intent Main = new Intent(this, MainActivity.class);
        Thread timer =  new Thread() {
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e  ){
                    e.printStackTrace();
                }
                finally {
                    startActivity(Main);
                    finish();
                }
            }
        };
        timer.start();
    }
}

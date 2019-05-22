package com.example.schiver.sethings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schiver.sethings.Utils.SharedPref;

public class MainActivity extends AppCompatActivity {
    Button logout;
    boolean session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = (Button) findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveSharefPref(getApplicationContext(),"session","false");
                Intent walkThrougIntent = new Intent(getApplicationContext(),WalkthroughActivity.class);
                startActivity(walkThrougIntent);
            }
        });
        mySession();
    }

    public void mySession(){
        session = Boolean.valueOf(SharedPref.readSharedPref(getApplicationContext(),"session","false"));
        if(!session){
            // Session unregistered
            Intent walkThroughIntent = new Intent(getApplicationContext(),WalkthroughActivity.class);
            startActivity(walkThroughIntent);
            finish();
        }else{
            // Session registered
            //Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
        }
    }
}

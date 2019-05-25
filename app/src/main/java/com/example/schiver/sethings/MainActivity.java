package com.example.schiver.sethings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
       // logout = (Button) findViewById(R.id.button);
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.saveSharefPref(getApplicationContext(),"session","false");
                Intent walkThrougIntent = new Intent(getApplicationContext(),WalkthroughActivity.class);
                startActivity(walkThrougIntent);
            }
        });*/

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        mySession();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.nav_home :
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_devices :
                    selectedFragment = new DevicesFragment();
                    break;
                case R.id.nav_config :
                    selectedFragment = new ConfigFragment();
                    break;
                case R.id.nav_usage :
                    selectedFragment = new UsageFragment();
                    break;
                case R.id.nav_help :
                    selectedFragment = new HelpFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,selectedFragment)
                    .commit();
            return true;
        }
    };

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

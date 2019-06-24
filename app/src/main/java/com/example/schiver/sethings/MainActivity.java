package com.example.schiver.sethings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schiver.sethings.Utils.SharedPref;

public class MainActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    Button logout;
    boolean session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            case R.id.account:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        SharedPref.saveSharefPref(getApplicationContext(),"session","false");
        Intent walkThrougIntent = new Intent(getApplicationContext(),WalkthroughActivity.class);
        startActivity(walkThrougIntent);
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

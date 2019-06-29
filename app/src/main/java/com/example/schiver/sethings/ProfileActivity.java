package com.example.schiver.sethings;

import android.arch.persistence.room.Database;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.UserData;
import com.example.schiver.sethings.Utils.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ImageView edit1;
    TextView userNameInput,nameInput,passwordInput;
    String sharedPrefUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Setting toolbar
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        mytoolbar.setTitle("Profile");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mytoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Edit component
        sharedPrefUsername = SharedPref.readSharedPref(getApplicationContext(),"username","");
        userNameInput = findViewById(R.id.textView10);
        userNameInput.setText(sharedPrefUsername);
        userNameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Username can't be edited",Toast.LENGTH_SHORT).show();
            }
        });
        nameInput = findViewById(R.id.textView19);
        nameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEditName(nameInput.getText().toString(),userNameInput.getText().toString());
            }
        });
        passwordInput = findViewById(R.id.textView20);
        passwordInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEditPass(userNameInput.getText().toString());
            }
        });
    }

    public void openDialogEditName(String editName, String userName){
        EditUsernameDialog editUname = new EditUsernameDialog();
        Bundle param = new Bundle();
        param.putString("editName",editName);
        param.putString("editUName",userName);
        editUname.setArguments(param);
        editUname.show(getSupportFragmentManager(),"Dialog Edit Name");
    }

    public void openDialogEditPass(String userName){
        EditPasswordDialog editPass = new EditPasswordDialog();
        Bundle param = new Bundle();
        param.putString("editUName",userName);
        editPass.setArguments(param);
        editPass.show(getSupportFragmentManager(),"Dialog Edit Pass");
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase myUserDb = FirebaseDatabase.getInstance();
        DatabaseReference myRefUserDb = myUserDb.getReference("SeThings-Users");
        myRefUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData myUserData = dataSnapshot.child(sharedPrefUsername).getValue(UserData.class);
                nameInput.setText(myUserData.getName());
                userNameInput.setText(myUserData.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

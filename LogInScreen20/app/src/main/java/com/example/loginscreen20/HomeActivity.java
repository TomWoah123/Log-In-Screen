package com.example.loginscreen20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class HomeActivity extends AppCompatActivity
{
    private EditText text;
    private Button signout;
    private FirebaseAuth firebase;

    public static final String SHARED_PREFS = "sharedPrefs";
    private User currentUser;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        text = findViewById( R.id.type_section );
        signout = findViewById( R.id.signout_button );
        gson = new GsonBuilder().setPrettyPrinting().create();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveText();
                signOutClick();
            }
        });
        firebase = FirebaseAuth.getInstance();
        loadData();
        update();
    }

    public void signOutClick()
    {
        FirebaseAuth.getInstance().signOut();
        Intent x = new Intent( HomeActivity.this, MainActivity.class );
        startActivity( x );
    }

    public void saveText()
    {
        SharedPreferences sharedPreferences = getSharedPreferences( SHARED_PREFS, MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        currentUser.setText( text.getText().toString() );
        String data = gson.toJson( currentUser );
        editor.putString( firebase.getUid(), data );
        editor.commit();
        editor.apply();
        Toast.makeText(HomeActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences( SHARED_PREFS, MODE_PRIVATE );
        String s = Service.loadData( firebase.getUid(), sharedPreferences );
        if ( s.length() > 0 )
        {
            currentUser = gson.fromJson( s, User.class );
        }
        else
        {
            currentUser = new User( firebase.getCurrentUser().getEmail(), "" );
        }
    }

    public void update()
    {
        text.setText( currentUser.getText() );
    }

}

package com.example.loginscreen20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private FirebaseAuth firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        login = findViewById( R.id.login_button );
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClick();
            }
        });
        register = findViewById( R.id.register_button );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClick();
            }
        });
        firebase = FirebaseAuth.getInstance();
    }

    public void loginClick()
    {
        String e = email.getText().toString();
        String p = password.getText().toString();
        if ( e.isEmpty() && p.isEmpty() )
        {
            Toast.makeText( MainActivity.this, "Please Enter in Fields", Toast.LENGTH_SHORT ).show();
        }
        else if ( e.isEmpty() )
        {
            email.setError( "Email is empty" );
            email.requestFocus();
            Toast.makeText( MainActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT ).show();
        }
        else if ( p.isEmpty() )
        {
            password.setError( "Password is empty" );
            password.requestFocus();
            Toast.makeText( MainActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT ).show();
        }
        else
        {
            firebase.signInWithEmailAndPassword( e, p ).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if ( task.isSuccessful() )
                    {
                        Toast.makeText( MainActivity.this, "You are logged in", Toast.LENGTH_SHORT ).show();
                        Intent i = new Intent( MainActivity.this, HomeActivity.class );
                        startActivity( i );
                    }
                    else
                    {
                        Toast.makeText( MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT ).show();
                    }
                }
            });
        }
    }

    public void registerClick()
    {
        Intent j = new Intent( MainActivity.this, RegisterActivity.class );
        startActivity( j );
    }
}

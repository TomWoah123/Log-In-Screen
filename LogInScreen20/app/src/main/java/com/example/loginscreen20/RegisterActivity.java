package com.example.loginscreen20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    private EditText email_new;
    private EditText password_new;
    private EditText password_retype;
    private Button create_button;
    private Button go_back;
    private Switch show_password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email_new = findViewById( R.id.email_new );
        password_new = findViewById( R.id.password_new );
        password_retype = findViewById( R.id.password_retype );
        TooltipCompat.setTooltipText( password_new, "hi" );
        create_button = findViewById( R.id.create_button );
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createButtonClick();
            }
        });
        go_back = findViewById( R.id.go_back );
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterActivity.this, MainActivity.class );
                startActivity( intent );
            }
        });
        show_password = findViewById( R.id.password_show );
        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                setPasswordVisible();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createButtonClick()
    {
        String e_new = email_new.getText().toString();
        String p_new = password_new.getText().toString();
        String p_retype = password_retype.getText().toString();
        if ( e_new.isEmpty() && p_new.isEmpty() )
        {
            Toast.makeText( RegisterActivity.this, "Fields are Empty", Toast.LENGTH_SHORT ).show();
        }
        else if ( e_new.isEmpty() )
        {
            email_new.setError( "Email is empty" );
            email_new.requestFocus();
            Toast.makeText( RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT ).show();
        }
        else if ( p_new.isEmpty() )
        {
            password_new.setError( "Password is empty" );
            password_new.requestFocus();
            Toast.makeText( RegisterActivity.this, "Please enter a password", Toast.LENGTH_SHORT ).show();
        }
        else if ( validatePassword( p_new ) )
        {
            if ( !p_new.equals( p_retype ) )
            {
                Toast.makeText( RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT ).show();
            }
            else
            {
                firebaseAuth.createUserWithEmailAndPassword( e_new, p_new ).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if ( task.isSuccessful() )
                        {
                            Toast.makeText( RegisterActivity.this, "User created", Toast.LENGTH_SHORT ).show();
                            Intent y = new Intent( RegisterActivity.this, MainActivity.class );
                            startActivity( y );
                        }
                        else
                        {
                            Toast.makeText( RegisterActivity.this, "User creation unsuccessful", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        }
    }

    private boolean validatePassword( String p )
    {
        if ( p.matches( "^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*\\d+)(?=.*[@$%#!]+).{8,16}$" ) )
        {
            return true;
        }
        else
        {
            String errorMessage = "Your password fails to meet the following requirements: \n";
            if ( p.length() > 16 || p.length() < 8 )
            {
                errorMessage += "Password length between 8 and 16 \n";
            }
            if ( !p.matches(".*[A-Z]+.*" ) )
            {
                errorMessage += "Password must have a capital letter \n";
            }
            if ( !p.matches( ".*[a-z]+.*" ) )
            {
                errorMessage += "Password must have a lowercase letter \n";
            }
            if ( !p.matches( ".*[0-9]+.*" ) )
            {
                errorMessage += "Password must have a number \n";
            }
            if ( !p.matches( ".*[@$#%!]+.*" ) )
            {
                errorMessage += "Password must contain one of the following characters: !,@,#,$,%";
            }
            Toast.makeText( RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT ).show();
        }
        return false;
    }

    public void setPasswordVisible()
    {
        if ( show_password.isChecked() )
        {
            password_new.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
            password_new.setSelection( password_new.getText().toString().length() );
            password_retype.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
            password_retype.setSelection( password_retype.getText().toString().length() );
        }
        else
        {
            password_new.setTransformationMethod( PasswordTransformationMethod.getInstance() );
            password_new.setSelection( password_new.getText().toString().length() );
            password_retype.setTransformationMethod( PasswordTransformationMethod.getInstance() );
            password_retype.setSelection( password_retype.getText().toString().length() );
        }
    }
}

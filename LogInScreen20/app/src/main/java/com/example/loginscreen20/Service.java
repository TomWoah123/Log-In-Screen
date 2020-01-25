package com.example.loginscreen20;

import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Service
{

    public static void saveText( String fileName, String data, SharedPreferences sharedPreferences )
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( fileName, data );
        editor.commit();
        editor.apply();
    }

    public static String loadData( String fileName, SharedPreferences sharedPreferences )
    {
        String s = sharedPreferences.getString( fileName, "" );
        return s;
    }
}

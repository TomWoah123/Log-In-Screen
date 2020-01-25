package com.example.loginscreen20;

public class User
{
    private String email;
    private String text;

    public User( String email, String text )
    {
        this.email = email;
        this.text = text;
    }

    public String getEmail()
    {
        return email;
    }

    public String getText()
    {
        return text;
    }

    public void setText( String txt )
    {
        text = txt;
    }

    public String toString()
    {
        return email + ": " + text;
    }
}

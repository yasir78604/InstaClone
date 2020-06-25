package com.example.serverapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameEditText;
    EditText passwordEditText;

    public void updateList(){
        Intent intent = new Intent(MainActivity.this,AllUsers.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layout || view.getId() == R.id.imageView){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void signUpButton(View view){


        if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Invalid Username/Password",Toast.LENGTH_SHORT).show();
        }else {
            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Toast.makeText(MainActivity.this,"User Successfully signed up",Toast.LENGTH_SHORT).show();
                        updateList();
                    }else {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void logInButton(View view){
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null && user != null){
                    Toast.makeText(MainActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                    updateList();
                }else {
                    Toast.makeText(MainActivity.this,   e.getMessage()  ,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        ImageView imageView = findViewById(R.id.imageView);
        ConstraintLayout layout = findViewById(R.id.layout);

        imageView.setOnClickListener(this);
        layout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            updateList();
        }


        ParseInstallation.getCurrentInstallation().saveInBackground();
    }



}
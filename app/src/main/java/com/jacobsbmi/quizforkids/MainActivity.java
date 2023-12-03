package com.jacobsbmi.quizforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    // Database helper object
    databaseHelper db;

    Button createAccountButton,signInButton;
    EditText idTextField,passwordTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_main페이지 보여주기
        setContentView(R.layout.activity_main);

        // Initialize UI components
        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        signInButton = (Button)findViewById(R.id.signInButton);

        idTextField =(EditText) findViewById(R.id.idTextField);
        passwordTextField = (EditText) findViewById(R.id.passwordTextField);

        // Initialize database helper object
        db = new databaseHelper(this);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, create_account.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idTextField.getText().toString().trim();
                String password = passwordTextField.getText().toString().trim();

                if (db.checkLogin(id, password)) {
                    // Login successful
                    Intent intent = new Intent(MainActivity.this, main_page.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login failed
                    Toast.makeText(MainActivity.this, "Check your Id or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 액션바를 설정합니다. 상단에 위치한 보라색 바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Start of the fragment transaction
        // Specify changes to be done
        Configuration configInfo = getResources().getConfiguration();
        if(configInfo.orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentLandScape fragmentLandscape = new fragmentLandScape();
            fragmentTransaction.replace(android.R.id.content, fragmentLandscape);
            // android.R.id.content - Activity's root view container
        } else {
            fragmentPortrait fragmentPortrait = new fragmentPortrait();
            fragmentTransaction.replace(android.R.id.content, fragmentPortrait);
        }

        fragmentTransaction.commit(); // Commit the transaction - finishes the transaction and applies the specified changes


    }
}
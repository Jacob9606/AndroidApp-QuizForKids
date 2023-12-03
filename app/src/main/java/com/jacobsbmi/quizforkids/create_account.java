package com.jacobsbmi.quizforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class create_account extends AppCompatActivity {
    private EditText nameTxtField;
    private EditText idTxtField;
    private EditText passwordTxtField;
    private Button createAccountButton;
    private databaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        nameTxtField = findViewById(R.id.nameTxtField);
        idTxtField = findViewById(R.id.idTxtField);
        passwordTxtField = findViewById(R.id.passwordTxtField);
        createAccountButton = findViewById(R.id.createAccountButton);

        dbHelper = new databaseHelper(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTxtField.getText().toString();
                String id = idTxtField.getText().toString();
                String password = passwordTxtField.getText().toString();

                if (name.isEmpty() || id.isEmpty() || password.isEmpty()) {
                    Toast.makeText(create_account.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAccountCreated = dbHelper.addData(id, password, name);
                    if (isAccountCreated) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Name", name);
                        editor.apply();

                        Toast.makeText(create_account.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(create_account.this, MainActivity.class);


                        startActivity(intent);

                    } else {
                        Toast.makeText(create_account.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

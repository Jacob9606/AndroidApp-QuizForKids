package com.jacobsbmi.quizforkids;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class change_password extends AppCompatActivity {
    // Database helper object
    private databaseHelper db;

    private EditText currentPasswordTxtField;
    private EditText newPasswordTxtField;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        // Initialize database helper object
        db = new databaseHelper(this);

        // Initialize UI components
        currentPasswordTxtField = findViewById(R.id.currentPasswordTxtField);
        newPasswordTxtField = findViewById(R.id.newPasswordTxtField);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordTxtField.getText().toString().trim();
                String newPassword = newPasswordTxtField.getText().toString().trim();

                // Check if the current password matches the one in the database
                if (db.checkPassword(currentPassword)) {
                    // Update the password in the database
                    if (db.updatePassword(newPassword)) {
                        Toast.makeText(change_password.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(change_password.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(change_password.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

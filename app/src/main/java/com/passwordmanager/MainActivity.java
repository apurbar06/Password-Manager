package com.passwordmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.MasterPasswordHandler;
import com.passwordmanager.handler.RecoveryGen;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public boolean codeRunning = false;
    RecoveryGen rg;
    private boolean masterPassword;

    private boolean getRecoveryGen() {
        try {
            if (rg == null) {
                rg = new RecoveryGen(this);
                Log.d(TAG, "getRecoveryGen: RecoveryGen init finish");
                Toast.makeText(this, "RecoveryGen init finish", Toast.LENGTH_SHORT).show();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_master_password);

        final MasterPasswordHandler master = new MasterPasswordHandler(this);


        if (master.hasPassword()) {
            // master password exist verify master password
            setContentView(R.layout.door1);
            Button enter = (Button) findViewById(R.id.enter);
            enter.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText passwordEditText = (EditText) findViewById(R.id.masterPassword);
                    String password = passwordEditText.getText().toString();
                    if (master.verifyPassword(password)) {
                        Intent intent = new Intent(MainActivity.this, AllItems.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // master password does't exist, add password
            Button set = (Button) findViewById(R.id.set);
            Button generate = (Button) findViewById(R.id.genarate);
            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!codeRunning) {
                        codeRunning = true;
                        EditText createPassword = (EditText) findViewById(R.id.createPassword);
                        EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
                        String password = createPassword.getText().toString();
                        String str = "";
                        if (password.equals(confirmPassword.getText().toString())) {
                            getRecoveryGen();
                            str = rg.encryptString(password);
                        } else {
                            Toast.makeText(MainActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                        codeRunning = false;
                    } else {
                        Toast.makeText(MainActivity.this, "code is running", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            set.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText createPassword = (EditText) findViewById(R.id.createPassword);
                    EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
                    String password = createPassword.getText().toString();
                    if (password.equals(confirmPassword.getText().toString())) {
                        master.setPassword(password);
                        Intent intent = new Intent(MainActivity.this, AllItems.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


}

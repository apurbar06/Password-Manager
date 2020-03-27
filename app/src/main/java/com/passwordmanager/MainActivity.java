package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.MasterPasswordHandler;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean masterPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_master_password);

        final MasterPasswordHandler master = new MasterPasswordHandler(this);

        //A functionality that extract the master password from database
        //and if it is not null then make the masterPassword false

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
            // master password does't exist add password
            Button set = (Button) findViewById(R.id.set);
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


//        setContentView(R.layout.door1);
//        Button enter = (Button) findViewById(R.id.enter);
//        enter.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                EditText passwordEditText = (EditText) findViewById(R.id.masterPassword);
//                if(passwordEditText.getText().toString().equals("1234")){
//                    Intent intent = new Intent(MainActivity.this, AllItems.class);
//                    startActivity(intent);
//                    finish();
//
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


}

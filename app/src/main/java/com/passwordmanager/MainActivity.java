package com.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean masterPassword ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //A functionality that extract the master password from database
        //and if it is not null then make the masterPassword false

       // masterPassword = true;
       // if (masterPassword){
            setContentView(R.layout.create_master_password);
            Button set = (Button) findViewById(R.id.set);
            set.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    EditText createPassword = (EditText) findViewById(R.id.createPassword);
                    EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
                    if(createPassword.getText().toString().equals(confirmPassword.getText().toString())){
                        //A functionality that will store the created masterPassword in database
                        Intent intent = new Intent(MainActivity.this, AllItems.class);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                    }
                }
            });
      //  }



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

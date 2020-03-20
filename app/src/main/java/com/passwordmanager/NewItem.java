package com.passwordmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class NewItem extends AppCompatActivity {
    private static final String TAG = "NewItem";
    EditText title;
    EditText username;
    EditText password;
    DataStorageHandler storageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.title);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        storageHandler = new DataStorageHandler(this);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(View view) {
        String t = title.getText().toString();
        String u = username.getText().toString();
        String p = password.getText().toString();
        ItemDataStore i = new ItemDataStore(1,t,u,p);
        storageHandler.saveItem(i);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        this.finish();

        Log.d(TAG, String.format("addItem: t=%s u=%s p=%s", t, u, p));
    }
}


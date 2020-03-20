package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class ItemDetails extends AppCompatActivity {
    private static final String TAG = "ItemDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String str = intent.getStringExtra("index");
        Log.d(TAG, "onCreate: " + str);
        int intId = Integer.parseInt(str);
        DataStorageHandler storage = new DataStorageHandler(this);
        ArrayList<ItemDataStore> Items = storage.getItems();
        ItemDataStore item = Items.get(intId);
        TextView title;
        TextView username;
        TextView password;
        title = findViewById(R.id.item_details_title);
        username = findViewById(R.id.item_details_username);
        password = findViewById(R.id.item_details_password);
        title.setText(item.getTitle());
        username.setText(item.getUsername());
        password.setText(item.getPassword());
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






}

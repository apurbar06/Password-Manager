package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
        TextView emailId;
        TextView mobileNo;
        TextView password;
        title = findViewById(R.id.item_details_title);
        username = findViewById(R.id.item_details_username);
        emailId = findViewById(R.id.item_details_emailId);
        mobileNo = findViewById(R.id.item_details_mobileNo);
        password = findViewById(R.id.item_details_password);
        title.setText(item.getTitle());
        username.setText(item.getUsername());
        emailId.setText(item.getEmailId());
        mobileNo.setText(item.getMobileNo());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return true;
    }




}

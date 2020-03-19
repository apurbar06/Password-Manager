package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<ItemDataStore> items = new ArrayList<>();
        ItemDataStore i = new ItemDataStore(1, "a", "b", "c");
        ItemDataStore i2 = new ItemDataStore(1, "a", "b", "c");
        items.add(i);
        items.add(i2);
        DataStorageHandler storage = new DataStorageHandler(this);
        String data = storage.makeSerializedData(items);
        boolean saved = storage.saveData(data);


        ArrayList<ItemDataStore> I = storage.getItems();
        Log.d(TAG, "onCreate: " + I.toString());


    }


    public void onClick(View view) {
        Intent intent = new Intent(this, ItemDetails.class);
        startActivity(intent);

    }
}

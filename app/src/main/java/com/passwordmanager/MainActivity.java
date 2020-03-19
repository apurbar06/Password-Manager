package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.root);
        ItemDataStore i = new ItemDataStore(1, "jgsjtyx", "bmdjmtr", "c38738fxgn");
        addItem(i);

//
//        ArrayList<ItemDataStore> items = new ArrayList<>();
//        ItemDataStore i = new ItemDataStore(1, "a", "b", "c");
//        ItemDataStore i2 = new ItemDataStore(1, "a", "b", "c");
//        items.add(i);
//        items.add(i2);
//        DataStorageHandler storage = new DataStorageHandler(this);
//        String data = storage.makeSerializedData(items);
//        boolean saved = storage.saveData(data);
//
//
//        ArrayList<ItemDataStore> I = storage.getItems();
//        Log.d(TAG, "onCreate: " + I.toString());


    }

    private void addItem(ItemDataStore item){

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item, null);
        TextView shortTitle = view.findViewById(R.id.short_title);
        TextView title = view.findViewById(R.id.title);
        String titleText = item.getTitle();
        String shortTitleText = item.getTitle().substring(0,1).toUpperCase();
        title.setText(titleText);
        shortTitle.setText(shortTitleText);
        linearLayout.addView(view);
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, ItemDetails.class);
        startActivity(intent);

    }
}

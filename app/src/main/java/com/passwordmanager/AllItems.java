package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class AllItems extends AppCompatActivity {

    public static final int OPEN_NEW_ACTIVITY = 12345;
    private static final String TAG = "Allitems";
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.root);
        refreshData();
    }


    public void refreshData() {
        linearLayout.removeAllViews();
        DataStorageHandler storage = new DataStorageHandler(this);
        ArrayList<ItemDataStore> Items = storage.sort(storage.getItems());
        for (ItemDataStore item : Items) {
            addItem(item);
        }
    }

    private void addItem(final ItemDataStore item) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = item.getId();
                Intent intent = new Intent(AllItems.this, ItemDetails.class);
                intent.putExtra("index", String.valueOf(id));
                startActivityForResult(intent, OPEN_NEW_ACTIVITY);

            }
        });
        TextView shortTitle = view.findViewById(R.id.short_title);
        TextView title = view.findViewById(R.id.title);
        String titleText = item.getTitle();
        String shortTitleText = item.getTitle().substring(0, 1).toUpperCase();
        title.setText(titleText);
        shortTitle.setText(shortTitleText);
        linearLayout.addView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_NEW_ACTIVITY) {
            refreshData();
        }

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, NewItem.class);
        startActivityForResult(intent, OPEN_NEW_ACTIVITY);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_delete, menu);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
}


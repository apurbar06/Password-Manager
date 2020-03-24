package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int OPEN_NEW_ACTIVITY = 12345;
    private static final String TAG = "MainActivity";
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.root);
//        ItemDataStore i = new ItemDataStore(1, "jgsjtyx", "bmdjmtr", "c38738fxgn");
//        addItem(i);


//        ArrayList<ItemDataStore> items = new ArrayList<>();
//        ItemDataStore i = new ItemDataStore(1, "a", "b", "c");
//        ItemDataStore i2 = new ItemDataStore(1, "a", "b", "c");
//        items.add(i);
//        items.add(i2);
//        String data = storage.makeSerializedData(items);
//        boolean saved = storage.saveData(data);


//        Log.d(TAG, "onCreate: " + I.toString());

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
                Intent intent = new Intent(MainActivity.this, ItemDetails.class);
                intent.putExtra("index", String.valueOf(id));
                startActivity(intent);

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
}

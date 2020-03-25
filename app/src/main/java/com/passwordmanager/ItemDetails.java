package com.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

public class ItemDetails extends AppCompatActivity {
    private static final String TAG = "ItemDetails";
    private int index;
    private DataStorageHandler storage;
    private int NEW_ACTIVITY = 1;


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
        index = Integer.parseInt(str);
        storage = new DataStorageHandler(this);
        init();
    }

    public void init() {
        ItemDataStore item = storage.getItem(index);
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
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle("Do you really want to delete the item")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                storage.deleteItem(index);
                                Toast.makeText(ItemDetails.this, "Deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(ItemDetails.this, "Item Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
                return true;
            case R.id.edit:
                Intent intent = new Intent(this, NewItem.class);
                intent.putExtra(NewItem.ID, String.valueOf(this.index));
                startActivityForResult(intent, NEW_ACTIVITY);

                // Log.d(TAG, "onOptionsItemSelected: edit");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == this.NEW_ACTIVITY) {
            // refresh data after edit
            init();
        }
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
    }


}

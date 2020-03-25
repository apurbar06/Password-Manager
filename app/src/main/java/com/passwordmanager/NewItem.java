package com.passwordmanager;

import android.content.Intent;
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

public class NewItem extends AppCompatActivity {
    public static final String ID = "id";
    private static final String TAG = "NewItem";
    EditText title;
    EditText username;
    EditText emailId;
    EditText mobileNo;
    EditText password;
    DataStorageHandler storageHandler;
    private boolean EDIT_ITEM = false;
    private Integer index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.title);
        username = findViewById(R.id.username);
        emailId = findViewById(R.id.emailId);
        mobileNo = findViewById(R.id.mobileNo);
        password = findViewById(R.id.password);
        storageHandler = new DataStorageHandler(this);

        index = getId();
        if (EDIT_ITEM) {
            // id user is trying to edit item fill the details from data storage
            ItemDataStore item = storageHandler.getItem(index);
            title.setText(item.getTitle());
            username.setText(item.getUsername());
            emailId.setText(item.getEmailId());
            mobileNo.setText(item.getMobileNo());
            password.setText(item.getPassword());
        }
    }

    /**
     * get id if user is trying to edit item
     * @return id
     */
    public Integer getId() {
        Intent intent = getIntent();
        String strId = intent.getStringExtra(NewItem.ID);
        if (strId != null) {
            EDIT_ITEM = true;
            return Integer.parseInt(strId);
        }
        return null;

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
        String e = emailId.getText().toString();
        String m = mobileNo.getText().toString();
        String p = password.getText().toString();
        if (EDIT_ITEM) {
            ItemDataStore item = new ItemDataStore(index, t, u, e, m, p);
            storageHandler.editItem(item);
            Toast.makeText(this, "edited", Toast.LENGTH_SHORT).show();
        } else {
            ItemDataStore item = new ItemDataStore(0, t, u, e, m, p);
            storageHandler.saveItem(item);
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }

        this.finish();

        Log.d(TAG, String.format("addItem: t=%s u=%s e=%s m=%s p=%s", t, u, e, m, p));
    }

}


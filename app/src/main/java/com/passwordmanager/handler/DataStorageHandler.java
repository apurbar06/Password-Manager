package com.passwordmanager.handler;

import android.content.Context;
import android.util.Log;

import com.passwordmanager.model.ItemDataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class DataStorageHandler {
    private static final String TAG = "DataStorageHandler";
    private String key = "passwords";
    private Context mContext;
    private String fileName = "passwords.txt";

    public DataStorageHandler(Context context) {
        this.mContext = context;
    }

    private String getData() {
        StringBuilder str = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(mContext.openFileInput(fileName));
            int i;
            while ((i = reader.read()) != -1) {
                str.append((char) i);
                // Log.d(TAG, String.format("loadData: i=%s (char) i=%s", i, str));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public ArrayList<ItemDataStore> getItems() {
        String data = getData();
        ArrayList<ItemDataStore> items = new ArrayList<>();
        try {
            // convert string to json object
            JSONObject jsn = new JSONObject(data);
            // extract key to get items
            String value = jsn.get(key).toString();
            // convert items to json array
            JSONArray strItems = new JSONArray(value);
            for (int i = 0; i < strItems.length(); i++) {
                // get individual item
                JSONObject strItem = (JSONObject) strItems.get(i);
                // extract keys
                int id = (int) strItem.get("id");
                String title = (String) strItem.get("title");
                String username = (String) strItem.get("username");
                String password = (String) strItem.get("password");
                // create data store
                ItemDataStore item = new ItemDataStore(id, title, username, password);
                // push item to array
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean saveData(String data) {
        boolean saved = false;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput(fileName, Context.MODE_PRIVATE));
            // Log.d(TAG, "file created: " + fileName);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            saved = true;
        } catch (Exception e) {
            Log.d(TAG, "error: ");
            e.printStackTrace();
        }
        return saved;
    }


    public String makeSerializedData(ArrayList<ItemDataStore> items) {
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject arr = new JSONObject();
        try {
            for (ItemDataStore item : items) {
                JSONObject obj = new JSONObject();
                obj.put("id", item.getId());
                obj.put("title", item.getTitle());
                obj.put("username", item.getUsername());
                obj.put("password", item.getPassword());
                list.add(obj);
            }
            arr.put(key, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr.toString();
    }
}

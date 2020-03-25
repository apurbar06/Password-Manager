package com.passwordmanager.handler;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.passwordmanager.model.ItemDataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataStorageHandler {
    private static final String TAG = "DataStorageHandler";
    private String key = "passwords";
    private Context mContext;
    private String fileName = "passwords.txt";

    public DataStorageHandler(Context context) {
        this.mContext = context;
    }

    /**
     * get saved password from device storage in string from
     *
     * @return string
     */
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

    /**
     * get saved passwords in a array from
     *
     * @return ArrayList<ItemDataStore>
     */
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
                JSONObject jsonObject = (JSONObject) strItems.get(i);
                // extract keys
                Log.d(TAG, "getItems: " + jsonObject);
                // create data store
                ItemDataStore item = new ItemDataStore(jsonObject);
                // push item to array
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * save data to device storage
     *
     * @param data {@link String}
     * @return boolean
     */
    public boolean saveData(String data) {
        boolean saved = false;
        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            Log.d(TAG, "file created: " + fileName);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            saved = true;
        } catch (Exception e) {
            Log.d(TAG, "error: ");
            e.printStackTrace();
        }
        return saved;
    }


    /**
     * converts array to string for saving purpose
     *
     * @param items {@link ArrayList<ItemDataStore>}
     * @return String
     */
    public String makeSerializedData(ArrayList<ItemDataStore> items) {
        ArrayList<JSONObject> list = new ArrayList<>();
        JSONObject arr = new JSONObject();
        try {
            for (ItemDataStore item : items) {
                list.add(item.toObject());
            }
            arr.put(key, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr.toString();
    }

    /**
     * helper function to save Item to storage
     *
     * @param i {@link ItemDataStore}
     * @return boolean
     */
    public boolean saveItem(ItemDataStore i) {
        ArrayList<ItemDataStore> itemDataStores = this.getItems();
        i.setId(itemDataStores.size());
        itemDataStores.add(i);
        String str = this.makeSerializedData(itemDataStores);
        this.saveData(str);
        return true;
    }


    public ArrayList<ItemDataStore> sort(ArrayList<ItemDataStore> items) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.sort(new SortByTitle());
        } else {
            Collections.sort(items, new SortByTitle());
        }

        return items;
    }

}

class SortByTitle implements Comparator<ItemDataStore> {
    @Override
    public int compare(ItemDataStore o1, ItemDataStore o2) {
        int firstChar = (o1.getTitle().toCharArray())[0];
        int secondChar = (o2.getTitle().toCharArray())[0];
        return firstChar - secondChar;
    }
}

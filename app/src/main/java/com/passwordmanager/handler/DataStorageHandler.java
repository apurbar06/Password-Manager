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

public class DataStorageHandler extends BaseStorageHandler {
    private static final String TAG = "DataStorageHandler";
    private String key = "passwords";
    private Context mContext;

    public DataStorageHandler(Context context) {
        super(context,"passwords.txt");
        this.mContext = context;
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
                item.setId(i);
                // push item to array
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public ItemDataStore getItem(int index) {
        ArrayList<ItemDataStore> items = getItems();
        for (ItemDataStore item : items) {
            int id = item.getId();
            if (id == index) {
                return item;
            }
        }
        return null;
    }

    /**
     * converts array to string for saving purpose
     *
     * @param items {@link ArrayList<ItemDataStore>}
     * @return String
     */
    private String makeSerializedData(ArrayList<ItemDataStore> items) {
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

    public void save(ArrayList<ItemDataStore> items) {
        String str = makeSerializedData(items);
        saveData(str);
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

    /**
     * edit item and save data to storage
     *
     * @param item edited item
     */
    public void editItem(ItemDataStore item) {
        ArrayList<ItemDataStore> items = getItems();
        ArrayList<ItemDataStore> editedItems = new ArrayList<>();
        for (ItemDataStore elm : items) {
            if (item.getId() == elm.getId()) {
                Log.d(TAG, "editItem: replace");
                editedItems.add(item);
            } else {
                Log.d(TAG, "editItem: new");
                editedItems.add(elm);
            }

        }
        save(editedItems);
    }


    public ArrayList<ItemDataStore> sort(ArrayList<ItemDataStore> items) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.sort(new SortByTitle());
        } else {
            Collections.sort(items, new SortByTitle());
        }

        return items;
    }

    /**
     * delete item at index and save
     *
     * @param index index to delete
     */
    public void deleteItem(int index) {
        ArrayList<ItemDataStore> items = getItems();
        ArrayList<ItemDataStore> newItems = new ArrayList<>();
        try {
            for (ItemDataStore item : items) {
                if (index == item.getId()) continue;
                newItems.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        save(newItems);
    }

    /**
     * delete item accordingly array of index and save
     *
     * @param index index to delete
     */
    public void deleteItems(ArrayList<Integer> index) {
        ArrayList<ItemDataStore> items = getItems();
        ArrayList<ItemDataStore> newItems = new ArrayList<>();
        try {
            for (ItemDataStore item : items) {
                // if the array contains the id skip the item
                // that deletes the item
                if (index.contains(item.getId())) continue;
                newItems.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        save(newItems);
    }

}

class SortByTitle implements Comparator<ItemDataStore> {
    @Override
    public int compare(ItemDataStore o1, ItemDataStore o2) {
//        int firstChar = (o1.getTitle().toCharArray())[0];
//        int secondChar = (o2.getTitle().toCharArray())[0];
        return o1.getTitle().compareTo(o2.getTitle());
    }
}

package com.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.passwordmanager.handler.DataStorageHandler;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;
import java.util.HashSet;

public class AllItems extends AppCompatActivity {

    public static final int OPEN_NEW_ACTIVITY = 12345;
    private static final String TAG = "Allitems";
    LinearLayout linearLayout;
    private boolean readyForDelete = false;
    private HashSet<Integer> listOfItemToDelete = new HashSet<>();
    private DataStorageHandler storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.root);
        refreshData();
    }


    public void refreshData() {
        // clear HashSet
        listOfItemToDelete.clear();
        linearLayout.removeAllViews();
        storage = new DataStorageHandler(this);
        ArrayList<ItemDataStore> Items = storage.sort(storage.getItems());
        for (ItemDataStore item : Items) {
            addItem(item, readyForDelete);
        }
    }

    private void addItem(final ItemDataStore item, boolean forDelete) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item, null);
        TextView shortTitle = view.findViewById(R.id.short_title);
        TextView title = view.findViewById(R.id.title);
        final CheckBox checkBox = view.findViewById(R.id.checkbox);

        // if for delete
        // add external listener in root view and check box to collect the id
        if (forDelete) {
            // root view listener
            View.OnClickListener viewListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get item id
                    int id = item.getId();
                    boolean checked = checkBox.isChecked();
                    if (checked) {
                        // if checkbox is checked remove the id form set and uncheck the checkbox
                        listOfItemToDelete.remove(id);
                        checkBox.setChecked(false);
                    } else {
                        // if checkbox is unchecked add the id to set and check the checkbox
                        listOfItemToDelete.add(id);
                        checkBox.setChecked(true);
                    }
                }
            };
            // checkbox listener
            View.OnClickListener checkBoxListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get item id
                    int id = item.getId();
                    boolean checked = checkBox.isChecked();
                    if (checked) {
                        // if checkbox is checked add the id to set.
                        // No need to uncheck the checkbox default behavior will uncheck the checkbox
                        listOfItemToDelete.add(id);
                    } else {
                        // if checkbox is checked remove the id form set.
                        // No need to uncheck the checkbox default behavior will check the checkbox
                        listOfItemToDelete.remove(id);
                    }
                }
            };
            //  if it is not for delete show checkbox
            checkBox.setVisibility(View.VISIBLE);
            // attach the listener
            view.setOnClickListener(viewListener);
            // attach the listener
            checkBox.setOnClickListener(checkBoxListener);
        } else {
            //  if it is not for delete hide checkbox
            checkBox.setVisibility(View.GONE);
            // if it is not for delete add listener to go to detail page
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = item.getId();
                    Intent intent = new Intent(AllItems.this, ItemDetails.class);
                    intent.putExtra("index", String.valueOf(id));
                    startActivityForResult(intent, OPEN_NEW_ACTIVITY);

                }
            });
        }
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
            readyForDelete = false;
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
        getMenuInflater().inflate(R.menu.menu_mark, menu);
        // adding the delete menu by default
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    /**
     * this method is called when the user click the three dot icon
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem mark = menu.findItem(R.id.mark);
        if (readyForDelete) {
            mark.setTitle("Un Mark");
            // if the array of index has items show the delete menu of hide it
            boolean shouldShowDeleteMenu = listOfItemToDelete.size() > 0;
            delete.setVisible(shouldShowDeleteMenu);
        } else {
            mark.setTitle("Mark");
            delete.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    private void deleteCheckedItems() {
        // convert HashSet to ArrList
        ArrayList<Integer> items = new ArrayList<>(listOfItemToDelete);
        // delete item by the index
        storage.deleteItems(items);
        // refresh data
        refreshData();
    }

    private void menuCheck() {
        // reverse the current status of readyForDelete
        readyForDelete = !readyForDelete;
        refreshData();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mark:
                menuCheck();
                break;
            case R.id.delete:
                deleteCheckedItems();
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: default " + item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


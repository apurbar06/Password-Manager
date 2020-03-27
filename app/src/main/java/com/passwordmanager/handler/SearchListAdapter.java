package com.passwordmanager.handler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.passwordmanager.AllItems;
import com.passwordmanager.R;
import com.passwordmanager.model.ItemDataStore;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter implements Filterable {


    private AllItems activity;
    private ArrayList<ItemDataStore> filteredList;
    private ArrayList<ItemDataStore> list;


    public SearchListAdapter(AllItems activity,ArrayList<ItemDataStore> list){
        this.activity = activity;
        this.list = list;
        this.filteredList = list;

        getFilter();
    }


    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        return filteredList.size();
    }

    /**
     * Get specific item from user list
     * @param position item index
     * @return list item
     */
    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    /**
     * Get user list item id
     * @param position item index
     * @return current item id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //final RecyclerView.ViewHolder holder;
        final ItemDataStore item = (ItemDataStore) getItem(position);

        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item, parent, false);
            //holder = new RecyclerView.ViewHolder();
        }


        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

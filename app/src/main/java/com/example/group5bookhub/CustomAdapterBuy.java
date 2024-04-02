package com.example.group5bookhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapterBuy extends ArrayAdapter<ImageAndText> {
    private Context context;
    private ArrayList<ImageAndText> objList;
    private ArrayList<ImageAndText> objListFull; // Full list to restore when search query is empty
    public CustomAdapterBuy(Context context, ArrayList<ImageAndText> objList) {
        super(context, R.layout.listview_layout, objList);
        this.context = context;
        this.objList = objList;
        this.objListFull = new ArrayList<>(objList); // Make a copy of the original list
    }

    public View getView(int position, View converView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_layout, parent, false);
        ImageAndText item = getItem(position);
        String itemText = item.getTxt();
        ImageView imageView = customView.findViewById(R.id.image);
        TextView textView = customView.findViewById(R.id.bookList);
        imageView.setImageResource(item.getImageID());
        textView.setText(itemText);
        return customView;
    }

    // Filter method to filter the list based on search query
    public void filterList(ArrayList<ImageAndText> filteredList) {
        objList.clear();
        objList.addAll(filteredList);
        notifyDataSetChanged();
    }

    // Method to restore the full list when search query is empty
    public void restoreFullList() {
        objList.clear();
        objList.addAll(objListFull);
        notifyDataSetChanged();
    }

}

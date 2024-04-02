package com.example.group5bookhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterSold extends ArrayAdapter<ImageAndText> {
    public CustomAdapterSold(Context context, ArrayList<ImageAndText> objList) {
        super(context, R.layout.listview_layout2, objList);
    }

    public View getView(int position, View converView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_layout2, parent, false);
        ImageAndText item = getItem(position);
        String itemText = item.getTxt();
        String itemStatus = item.getStatus();
        ImageView imageView = customView.findViewById(R.id.image);
        TextView bl = customView.findViewById(R.id.bookList);
        TextView st = customView.findViewById(R.id.orderStatus);
        imageView.setImageResource(item.getImageID());
        bl.setText(itemText);
        st.setText(itemStatus);
        return customView;
    }
}

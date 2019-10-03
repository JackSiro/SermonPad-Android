package com.jackson_siro.sermonpad.ui.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jackson_siro.sermonpad.R;
import com.jackson_siro.sermonpad.tools.*;

import java.util.ArrayList;

public class AdapterChecker extends ArrayAdapter<AppListItem> {
    ArrayList<AppListItem> ItemList;
    Activity setActivity;

    public AdapterChecker(Context context, int textViewResourceId, ArrayList<AppListItem> ItemList) {
        super(context, textViewResourceId, ItemList);
        this.ItemList = new ArrayList<>();
        this.ItemList.addAll(ItemList);
    }

    private class ViewHolder { CheckBox checker; TextView title; TextView content; }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;  Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)setActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_checker, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.checker = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
            holder.checker.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    AppListItem lItem = (AppListItem) cb.getTag();
                    lItem.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppListItem listItem = ItemList.get(position);
        holder.title.setText(listItem.getTitle());
        holder.content.setText(listItem.getDescri());
        holder.checker.setChecked(listItem.isSelected());
        holder.checker.setTag(listItem);
        return convertView;
    }
}
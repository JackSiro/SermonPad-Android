package com.jackson_siro.sermonpad.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jackson_siro.sermonpad.*;
import com.jackson_siro.sermonpad.tools.*;

import java.util.ArrayList;
import java.util.List;

public class AdapterNote extends BaseAdapter {
    List<AppListNote> notes = new ArrayList<>();
    Context context;

    public AdapterNote(Context context) {
        this.context = context;
    }

    public void add(AppListNote notes) {
        this.notes.add(notes);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        NoteViewHolder holder = new NoteViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        AppListNote note = notes.get(i);

        convertView = messageInflater.inflate(R.layout.adapter_note, null);
        holder.texts = convertView.findViewById(R.id.texts);
        holder.dates = convertView.findViewById(R.id.dates);
        convertView.setTag(holder);

        holder.texts.setText(note.getTexts());
        holder.dates.setText(note.getDates());
        return convertView;
    }

}

class NoteViewHolder {
    public TextView texts, dates;
}
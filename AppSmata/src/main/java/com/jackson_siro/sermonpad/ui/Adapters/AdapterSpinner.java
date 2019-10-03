package com.jackson_siro.sermonpad.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackson_siro.sermonpad.R;

public class AdapterSpinner extends BaseAdapter{

    private Activity setActivity;
    private String[] strID, strTitle;
    static Context mcontext;
    private static LayoutInflater inflater = null;

    public AdapterSpinner(Activity uiActivity, String[] itemid, String[] title) {
        setActivity = uiActivity;
        strID = itemid;
        strTitle = title;
        inflater = (LayoutInflater)setActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return strID.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView textID, textTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View finalView = convertView;
        ViewHolder holder;

        if(convertView==null){
            finalView = inflater.inflate(R.layout.adapter_spinner, null);

            holder = new ViewHolder();
            holder.textID = finalView.findViewById(R.id.itemid);
            holder.textTitle = finalView.findViewById(R.id.title);

            finalView.setTag( holder );
        }
        else holder=(ViewHolder)finalView.getTag();

        holder.textID.setText(strID[position]);
        holder.textTitle.setText(strTitle[position]);

        return finalView;
    }
}
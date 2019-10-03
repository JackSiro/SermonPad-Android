package com.jackson_siro.sermonpad.ui.Adapters;

import com.jackson_siro.sermonpad.*;
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

public class AdapterList extends BaseAdapter{

    private Activity setActivity;
    private String[] strIcon, strID, strTitle, strDates, strExtra, strContent;
    static Context mcontext;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public AdapterList(Activity uiActivity, String[] icon, String[] itemid, String[] title, String[] dates, String[] extra, String[] content) {
        setActivity = uiActivity;
        strIcon = icon;
        strID = itemid;
        strTitle = title;
        strDates = dates;
        strExtra = extra;
        strContent = content;
        inflater = (LayoutInflater)setActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader = new ImageLoader(setActivity.getApplicationContext());
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
        public TextView textID, textTitle, textDates, textExtra, textContent;
        public ImageView textIcon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View finalView = convertView;
        ViewHolder holder;

        if(convertView==null){
            finalView = inflater.inflate(R.layout.adapter_list, null);

            holder = new ViewHolder();
            holder.textID = finalView.findViewById(R.id.itemid);
            holder.textTitle = finalView.findViewById(R.id.title);
            holder.textDates = finalView.findViewById(R.id.dates);
            holder.textExtra = finalView.findViewById(R.id.extra);
            holder.textContent = finalView.findViewById(R.id.content);
            holder.textIcon = finalView.findViewById(R.id.icon);

            finalView.setTag( holder );
        }
        else holder=(ViewHolder)finalView.getTag();

        holder.textID.setText(strID[position]);
        holder.textTitle.setText(strTitle[position]);
        holder.textDates.setText(strDates[position]);
        holder.textExtra.setText(strExtra[position]);
        holder.textContent.setText(strContent[position]);
        ImageView itemImage = holder.textIcon;
        //imageLoader.DisplayImage(strIcon[position], itemImage);

        return finalView;
    }

    public static Bitmap decodeFile(Context context, int resId) {
        try {
            // decode image size
            mcontext=context;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(mcontext.getResources(), resId, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 200;
            int width_tmp = o. outWidth, height_tmp = o. outHeight ;
            int scale = 1;
            while ( true)
            {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break ;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2. inSampleSize = scale;
            return BitmapFactory.decodeResource( mcontext.getResources(), resId, o2);
        }
        catch (Exception e) {}
        return null;
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap. createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle((( float ) targetWidth - 1) / 2,
                ((float ) targetHeight - 1) / 2, (Math. min((( float ) targetWidth),
                        ((float ) targetHeight)) / 2), Path.Direction. CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

}
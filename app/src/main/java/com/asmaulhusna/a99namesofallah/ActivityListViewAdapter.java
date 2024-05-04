package com.asmaulhusna.a99namesofallah;

/**
 * Created by Usman Ali on 03/04/2019.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class ActivityListViewAdapter extends BaseAdapter {
    String[] Meanings;
    String[] Names;
    Context context;
    Typeface font;
    int f47i;
    LayoutInflater inflater;
    int[] nameId;
    public ActivityListViewAdapter(Context context) {
        this.context = context;
    }
    public int getCount() {
        return Integer.valueOf(99).intValue();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


            this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = this.inflater.inflate(R.layout.activity_listview_item, parent, false);

            TextView Meaning = (TextView) itemView.findViewById(R.id.LVmeaning);
            TextView nameImage = (TextView) itemView.findViewById(R.id.LVimg);
            TextView name = (TextView) itemView.findViewById(R.id.LVname);
            name.setText(MainActivity.names[position]);
            Meaning.setText(MainActivity.meaning[position]);
            nameImage.setText(MainActivity.imagesSmall[position]);
            return itemView;
    }
}


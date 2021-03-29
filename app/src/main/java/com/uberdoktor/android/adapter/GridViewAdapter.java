package com.uberdoktor.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uberdoktor.android.R;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String[] numberOfSpeciality;
    int[] specialityImage;

    public GridViewAdapter(Context context, String[] numberOfSpeciality, int[] specialityImage) {
        this.context = context;
        this.numberOfSpeciality = numberOfSpeciality;
        this.specialityImage = specialityImage;
    }

    @Override
    public int getCount() {
        return numberOfSpeciality.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.speciality_row_item, null);
        }
        ImageView imageView = convertView.findViewById(R.id.img_view);
        TextView textView = convertView.findViewById(R.id.specialist_tv_title);
        imageView.setImageResource(specialityImage[position]);
        textView.setText(numberOfSpeciality[position]);

        return convertView;
    }
}

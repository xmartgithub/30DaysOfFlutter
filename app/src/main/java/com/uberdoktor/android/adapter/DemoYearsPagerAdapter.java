package com.uberdoktor.android.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.uberdoktor.android.R;

import java.util.ArrayList;
import java.util.List;

public class DemoYearsPagerAdapter extends PagerAdapter {

    private List<String> mItems = new ArrayList<>();
    private List<TextView> mTextViews = new ArrayList<>();

    public DemoYearsPagerAdapter() {
    }

    public DemoYearsPagerAdapter(List<TextView> mTextViews) {
        this.mTextViews = mTextViews;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = view.findViewById(R.id.title);
        textView.setText(mItems.get(position));

//        TextView view1 = mTextViews.get(position);
//        container.addView(view1);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return mItems.get(position);
    }

    public void addAll(List<String> items) {
        mItems = new ArrayList<>(items);
    }
}
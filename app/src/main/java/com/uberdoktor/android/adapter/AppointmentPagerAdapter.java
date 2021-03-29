//package com.uberdoktor.android;
//
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import java.util.List;
//
//public class AppointmentPagerAdapter extends FragmentPagerAdapter {
//
//    int tabs;
//    List<String> dates;
//
//    public AppointmentPagerAdapter(@NonNull FragmentManager fm, int tabs, List<String> dates) {
//        super(fm);
//        this.tabs = tabs;
//        this.dates =dates;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return  view == object;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return DynamicFragment.newInstance(dates.get(position));
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return POSITION_NONE;
//    }
//
//    @Override
//    public int getCount() {
//        return tabs;
//    }
//}

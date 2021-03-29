package com.uberdoktor.android.navigation;

public class GroupItem extends BaseItem {
    private int mLevel;

    public GroupItem(String name) {
        super(name);
        mLevel = 0;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }


}

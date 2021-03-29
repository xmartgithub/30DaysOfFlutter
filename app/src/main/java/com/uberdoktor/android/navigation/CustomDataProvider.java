package com.uberdoktor.android.navigation;


import java.util.ArrayList;
import java.util.List;

public class CustomDataProvider {

    private static final int MAX_LEVELS = 3;

    private static final int LEVEL_1 = 1;

    private static List<BaseItem> mMenu = new ArrayList<>();

    public static List<BaseItem> getInitialItems() {
        //return getSubItems(new GroupItem("root"));

        List<BaseItem> rootMenu = new ArrayList<>();

        rootMenu.add(new Item("Overview"));
        rootMenu.add(new Item("Medical History"));
        rootMenu.add(new Item("Payments"));
        rootMenu.add(new Item("Consultations"));
        rootMenu.add(new GroupItem("Services"));
        rootMenu.add(new Item("Help"));
        rootMenu.add(new Item("Logout"));
        return rootMenu;
    }

    public static List<BaseItem> getSubItems(BaseItem baseItem) {

        List<BaseItem> result = new ArrayList<>();
        int level = ((GroupItem) baseItem).getLevel() + 1;
        String menuItem = baseItem.getName();

        GroupItem groupItem = (GroupItem) baseItem;

        if (groupItem.getLevel() >= MAX_LEVELS) {
            return null;
        }
        if (level == LEVEL_1) {
            if ("SERVICES".equals(menuItem.toUpperCase())) {
                result = getListServices();
            }

        }

        return result;
    }

    public static boolean isExpandable(BaseItem baseItem) {
        return baseItem instanceof GroupItem;
    }


    private static List<BaseItem> getListServices() {
        List<BaseItem> list = new ArrayList<>();
        list.add(new Item("Pharmacy"));
        list.add(new Item("Lab"));
        return list;
    }


}

package com.easylife.letsgo;

/**
 * Created by xtgsy on 2015/11/8.
 */

import android.content.Context;

public class DestinationCard {
    String name;

    String picName;

    public DestinationCard(String name, String picName) {
        this.name = name;
        this.picName = picName;
    }

    public int getImageResourceId(Context context) {
        try {
            return context.getResources().getIdentifier(this.picName, "drawable", context.getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

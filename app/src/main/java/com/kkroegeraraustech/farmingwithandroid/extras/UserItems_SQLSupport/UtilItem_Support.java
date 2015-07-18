package com.kkroegeraraustech.farmingwithandroid.extras.UserItems_SQLSupport;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ken Heron Systems on 6/23/2015.
 */
public class UtilItem_Support {

    public static final StringBuilder sb = new StringBuilder();

    public static String concat(Object... objects) {
        sb.setLength(0);
        for (Object obj : objects) {
            sb.append(obj);
        }
        return sb.toString();
    }

    public static String listToCsv(List list) {
        if (list == null || list.isEmpty())
            return "";

        sb.setLength(0);
        for (Object obj : list) {
            sb.append(",").append(obj);
        }
        return sb.toString().substring(1);
    }

    public static List csvToList(String csv) {
        if (csv == null || "".equals(csv.trim()))
            return new ArrayList();

        return Arrays.asList(csv.split(","));
    }

    public static void fixBackgroundRepeat(View view) {
        Drawable bg = view.getBackground();
        if(bg != null) {
            if(bg instanceof BitmapDrawable) {
                BitmapDrawable bmp = (BitmapDrawable) bg;
                bmp.mutate(); // make sure that we aren't sharing state anymore
                bmp.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }
}

package com.kkroegeraraustech.farmingwithandroid.views;

import android.widget.EditText;

/**
 * Created by Ken Heron Systems on 6/2/2015.
 */
public class Util {
    public static boolean hasValidContents(EditText editText) {
        if (editText != null
                && editText.getText() != null
                && editText.getText().toString() != null
                && editText.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }
}

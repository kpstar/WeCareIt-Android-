package com.wecareit.common;

import android.util.Log;

import com.google.gson.Gson;

public class Message {
    public static void showMessage(String message) {
        Log.e(Global.TAG, "WeCareIt -> " + message);
    }

    public static void showObject(Object object) {
        Gson gson = new Gson();
        Log.e(Global.TAG, "WeCareIt -> " + gson.toJson(object));
    }
}

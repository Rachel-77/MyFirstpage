package com.waka.basic;

import com.google.gson.Gson;

public class GsonUtil {
    public static <T> T safeFromJson(String element, Class<T> clazz) {
        if (element == null) {
            return null;
        }
        try {
            return new Gson().fromJson(element, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    }


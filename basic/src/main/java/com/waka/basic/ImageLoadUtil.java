package com.waka.basic;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoadUtil {
    public static void image(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}
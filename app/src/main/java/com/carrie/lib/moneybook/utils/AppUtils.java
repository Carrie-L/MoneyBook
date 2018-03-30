package com.carrie.lib.moneybook.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.util.List;

/**
 * Created by Carrie on 2018/3/30.
 * 公共方法
 */

public class AppUtils {

    public static Drawable getTintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colorStateList);
        return wrappedDrawable;
    }

    public static <T> void logListString(List<T> list) {
        if (list != null && list.size() > 0) {
            LogUtil.i("logListString", list.get(0).getClass().getSimpleName() + ": " + list.size() + "," + list.toString());
        } else {
            LogUtil.e("logListString", "list is empty");
        }
    }


}

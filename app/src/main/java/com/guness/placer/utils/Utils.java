package com.guness.placer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.guness.placer.R;

/**
 * Created by guness on 11/08/16.
 */
public class Utils {

    @ColorInt
    public static int[] loadPlaceColors(Context context) {
        return new int[]{
                getColor(context, R.color.color00),
                getColor(context, R.color.color01),
                getColor(context, R.color.color02),
                getColor(context, R.color.color03),
                getColor(context, R.color.color04),
                getColor(context, R.color.color05),
                getColor(context, R.color.color06),
                getColor(context, R.color.color07),
                getColor(context, R.color.color08),
                getColor(context, R.color.color09),
                getColor(context, R.color.color10),
                getColor(context, R.color.color11),
                getColor(context, R.color.color12)
        };
    }

    @ColorInt
    public static int[] loadRouteColors(Context context) {
        return new int[]{
                getColor(context, R.color.route0),
                getColor(context, R.color.route1),
                getColor(context, R.color.route2),
                getColor(context, R.color.route3)
        };
    }


    @SuppressWarnings("deprecation")
    @ColorInt
    private static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(resId);
        } else {
            return context.getResources().getColor(resId);
        }
    }

    public static Bitmap[] loadPlaceMarkers(Context context) {

        int[] placeColors = loadPlaceColors(context);

        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker);
        Bitmap[] maps = new Bitmap[placeColors.length];
        for (int i = 0; i < placeColors.length; i++) {
            maps[i] = filterBitmap(sourceBitmap, placeColors[i]);
        }

        return maps;
    }

    private static Bitmap filterBitmap(Bitmap sourceBitmap, int placeColor) {
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(placeColor, PorterDuff.Mode.SRC_ATOP);

        Paint paint = new Paint();
        paint.setColorFilter(colorFilter);

        Bitmap resultBitmap = sourceBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);

        return resultBitmap;
    }
}

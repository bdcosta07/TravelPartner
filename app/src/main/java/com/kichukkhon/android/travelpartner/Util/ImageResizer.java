package com.kichukkhon.android.travelpartner.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by linux64 on 8/27/16.
 */
public class ImageResizer {

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int targetW, int targetH) {
        // Raw height and width of image
        final int photoH = options.outHeight;
        final int photoW = options.outWidth;
        int scaleFactor = 1;

        /*if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;


            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }*/

        scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        return scaleFactor;
    }


    public static Bitmap decodeSampledBitmapFromFile(String imagePath, int targetW, int targetH) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        /* Figure out which way needs to be reduced less */
        int scaleFactor = calculateInSampleSize(options, targetW, targetH);

        /* Set bitmap options to scale the image decode target */
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        return bitmap;
    }
}

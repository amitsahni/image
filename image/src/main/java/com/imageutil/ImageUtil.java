package com.imageutil;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;


/**
 * The type Image util.
 */
public class ImageUtil {
    private static volatile ImageUtil sImageUtil;

    private ImageUtil() {

    }

    /**
     * Get image util.
     *
     * @return the image util
     */
    public static ImageUtil get() {
        if (sImageUtil == null) {
            synchronized (ImageUtil.class) {
                if (sImageUtil == null) {
                    sImageUtil = new ImageUtil();
                }
            }
        }
        return sImageUtil;
    }

    /**
     * With builder.
     * <p>
     * OnlyDownload
     *
     * @param context the context
     * @return the builder
     */
    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }


    public void clearCache(@NonNull final Context context) {
        Glide.get(context).clearMemory();
        new Thread(new Runnable() {
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }
}

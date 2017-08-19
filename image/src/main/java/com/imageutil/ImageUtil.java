package com.imageutil;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;


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
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    @Deprecated
    public static ImageParam.Builder with(@NonNull Context context, @NonNull String url) {
        return new ImageParam.Builder(context, url, null);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    @Deprecated
    public static ImageParam.Builder with(@NonNull Activity context, @NonNull String url) {
        return new ImageParam.Builder(context, url, null);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    public static ImageParam.Builder with(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        return new ImageParam.Builder(context, url, imageView);
    }

    /**
     * With builder.
     *
     * @param context the context
     * @param url     the url
     * @return the builder
     */
    public static ImageParam.Builder with(@NonNull Activity context, @NonNull String url, @NonNull ImageView imageView) {
        return new ImageParam.Builder(context, url, imageView);
    }
}

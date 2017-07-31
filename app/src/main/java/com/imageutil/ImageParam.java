package com.imageutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.Transformation;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Image param.
 */
public class ImageParam {
    /**
     * The Context.
     */
    protected Context context;
    /**
     * The Activity context.
     */
    protected Activity activityContext;
    /**
     * The Url.
     */
    protected String url, /**
     * The Disable cache key.
     */
    disableCacheKey;
    /**
     * The Image type.
     */
    protected ImageType imageType = ImageType.URL;
    /**
     * The Loading thumbnail.
     */
    protected int loadingThumbnail = -1, /**
     * The Error thumbnail.
     */
    errorThumbnail = -1, /**
     * The Task id.
     */
    taskId, /**
     * The Height.
     */
    height, /**
     * The Width.
     */
    width;
    /**
     * The Need bitmap.
     */
    protected boolean needBitmap = false, /**
     * The Disable cache.
     */
    disableCache = false, /**
     * The Clear cache.
     */
    clearCache = false;
    /**
     * The Header.
     */
    protected Map<String, String> header = new LinkedHashMap<>();
    /**
     * The Image mView.
     */
    protected ImageView imageView;
    /**
     * The Progress bar.
     */
    protected ProgressBar progressBar;
    /**
     * The Callback.
     */
    protected onCallback callback;
    /**
     * The Config.
     */
    protected Bitmap.Config config = Bitmap.Config.RGB_565;

    protected Transformation<Bitmap>[] transformation;

    /**
     * The enum Image type.
     */
    public enum ImageType {
        /**
         * Url image type.
         */
        URL, /**
         * Uri image type.
         */
        URI, /**
         * File image type.
         */
        FILE
    }

    /**
     * The interface On callback.
     */
    public interface onCallback {
        /**
         * On bitmap received.
         *
         * @param bitmap the bitmap
         * @param file   the file
         * @param taskId the task id
         */
        void onBitmapReceived(Bitmap bitmap, File file, int taskId);
    }

    /**
     * The type Callback.
     */
    public abstract class Callback implements onCallback {

    }
}

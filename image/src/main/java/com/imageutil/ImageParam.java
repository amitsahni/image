package com.imageutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.Transformation;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Image param.
 */
public class ImageParam {
    /**
     * The Context.
     */
    private Context context;
    /**
     * The Activity context.
     */
    private Activity activityContext;
    /**
     * The Url.
     */
    private String url, /**
     * The Disable cache key.
     */
    disableCacheKey;
    /**
     * The Image type.
     */
    private ImageType imageType = ImageType.URL;
    /**
     * The Loading thumbnail.
     */
    private int loadingThumbnail = -1, /**
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
    private boolean needBitmap = false, /**
     * The Disable cache.
     */
    disableCache = false, /**
     * The Clear cache.
     */
    clearCache = false;
    /**
     * The Header.
     */
    private Map<String, String> header = new LinkedHashMap<>();
    /**
     * The Image mView.
     */
    private ImageView imageView;
    /**
     * The Progress bar.
     */
    private ProgressBar progressBar;
    /**
     * The Callback.
     */
    private onCallback callback;
    /**
     * The Config.
     */
    private Bitmap.Config config = Bitmap.Config.RGB_565;

    private Transformation<Bitmap> transformation;

    private ImageView.ScaleType scaleType = null;

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
         * @param taskId the task id
         */
        void onBitmapReceived(Bitmap bitmap, int taskId);
    }

    /**
     * The type Callback.
     */
    public abstract class Callback implements onCallback {

    }

    public Context getContext() {
        return context;
    }

    public Activity getActivityContext() {
        return activityContext;
    }

    public String getUrl() {
        return url;
    }

    public String getDisableCacheKey() {
        return disableCacheKey;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public int getLoadingThumbnail() {
        return loadingThumbnail;
    }

    public int getErrorThumbnail() {
        return errorThumbnail;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isNeedBitmap() {
        return needBitmap;
    }

    public boolean isDisableCache() {
        return disableCache;
    }

    public boolean isClearCache() {
        return clearCache;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public onCallback getCallback() {
        return callback;
    }

    public Bitmap.Config getConfig() {
        return config;
    }

    public Transformation<Bitmap> getTransformation() {
        return transformation;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private ImageParam imageParam;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         * @param url     the url
         */
        public Builder(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
            imageParam = new ImageParam();
            imageParam.context = context;
            imageParam.activityContext = null;
            imageParam.url = url;
            imageParam.imageView = imageView;
            imageParam.imageType = ImageType.URL;
        }

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         * @param url     the url
         */
        public Builder(@NonNull Activity context, @NonNull String url, @NonNull ImageView imageView) {
            imageParam = new ImageParam();
            imageParam.activityContext = context;
            imageParam.context = context;
            imageParam.url = url;
            imageParam.imageView = imageView;
            imageParam.imageType = ImageType.URL;
        }

        /**
         * Image type builder.
         *
         * @param imageType the image type
         * @return the builder
         */
        public Builder imageType(@NonNull ImageParam.ImageType imageType) {
            imageParam.imageType = imageType;
            return this;
        }

        /**
         * Thumbnail builder.
         *
         * @param loadingThumb the loading thumb
         * @param errorThumb   the error thumb
         * @return the builder
         */
        public Builder thumbnail(int loadingThumb, int errorThumb) {
            imageParam.loadingThumbnail = loadingThumb;
            imageParam.errorThumbnail = errorThumb;
            return this;
        }

        /**
         * Task id builder.
         *
         * @param taskId the task id
         * @return the builder
         */
        public Builder taskId(int taskId) {
            imageParam.taskId = taskId;
            return this;
        }

        /**
         * Headers builder.
         *
         * @param headers the headers
         * @return the builder
         */
        public Builder headers(@NonNull Map<String, String> headers) {
            imageParam.header = headers;
            return this;
        }

        /**
         * Need bitmap builder.
         *
         * @param needBitmap the need bitmap
         * @param taskId     the task id
         * @return the builder
         */
        public Builder needBitmap(boolean needBitmap, int taskId) {
            imageParam.needBitmap = needBitmap;
            imageParam.taskId = taskId;
            return this;
        }

        /**
         * Callback builder.
         *
         * @param callback the callback
         * @return the builder
         */
        public Builder callback(@NonNull ImageParam.onCallback callback) {
            imageParam.callback = callback;
            return this;
        }

        /**
         * Resize builder.
         *
         * @param height the height
         * @param width  the width
         * @return the builder
         */
        public Builder resize(int height, int width) {
            imageParam.height = height;
            imageParam.width = width;
            return this;
        }

        /**
         * Disable cache builder.
         *
         * @param disableCache the disable cache
         * @return the builder
         */
        public Builder disableCache(boolean disableCache) {
            imageParam.disableCache = disableCache;
            return this;
        }

        /**
         * Disable cache builder.
         *
         * @param key the key
         * @return the builder
         */
        public Builder disableCache(String key) {
            imageParam.disableCache = true;
            imageParam.disableCacheKey = key;
            return this;
        }

        /**
         * Clear whole cache builder.
         *
         * @return the builder
         */
        public Builder clearWholeCache() {
            imageParam.clearCache = true;
            return this;
        }

        public Builder transform(Transformation<Bitmap> transformations) {
            imageParam.transformation = transformations;
            return this;
        }

        public Builder scaleType(@NonNull ImageView.ScaleType scaleType) {
            imageParam.scaleType = scaleType;
            return this;
        }

        /**
         * Build.
         */
        public void build() {
            new GlideUtil().setImage(imageParam);
        }
    }


}

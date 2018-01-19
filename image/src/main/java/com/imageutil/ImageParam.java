package com.imageutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.imageutil.listener.DownloadListener;
import com.imageutil.listener.LoaderListener;
import com.imageutil.listener.ProgressListener;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Image param.
 */
public class ImageParam {
    Context context;
    ProgressListener progressListener;
    LoaderListener loaderListener;
    DownloadListener downloadListener;
    String url, disableCacheKey;

    ImageType imageType = ImageType.URL;

    int loadingThumbnail = -1,
            errorThumbnail = -1,
            taskId = -1,
            height,
            width;

    boolean disableCache = false, clearCache = false;

    Map<String, String> header = new LinkedHashMap<>();

    ImageView imageView;

    ImageView.ScaleType scaleType = null;

    Bitmap.Config config = Bitmap.Config.RGB_565;

    Transformation<Bitmap> transformation;

    public enum ImageType {
        URL,
        URI,
        FILE,
        DOWNLOAD
    }


}

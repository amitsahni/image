package com.imageutil;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.imageutil.listener.LoaderListener;
import com.imageutil.listener.ProgressListener;

import java.util.Map;

/**
 * Created by clickapps on 19/1/18.
 */

public interface ImageProperty<T> {

    T thumbnail(int loadingThumb, int errorThumb);

    T header(@NonNull Map<String, String> headers);

    T resize(int height, int width);

    T cache(boolean isCache);

    T transform(Transformation<Bitmap> transformations);

    T progressListener(@NonNull ProgressListener listener);

    T loaderListener(@NonNull LoaderListener listener);
}

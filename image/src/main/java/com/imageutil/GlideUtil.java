package com.imageutil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * The type Glide util.
 */
public class GlideUtil {
    private OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
    private static final long CONNECT_TIMEOUT_MILLIS = 10 * 1000, READ_TIMEOUT_MILLIS = 20 * 1000;
    private HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor();
    public static boolean LOG_ENABLED = false;

    /**
     * Sets image.
     *
     * @param imageParam the image param
     */
    public void setImage(final ImageParam imageParam) {
        if (BuildConfig.DEBUG) {
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        mOkHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (imageParam.getHeader() != null && !imageParam.getHeader().isEmpty()) {
                    for (Map.Entry<String, String> entry : imageParam.getHeader().entrySet()) {
                        request = request.newBuilder().addHeader(entry.getKey(), entry.getValue()).build();
                    }
                }
                return chain.proceed(request);
            }
        });
        mOkHttpClientBuilder.addInterceptor(mInterceptor);
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(mOkHttpClientBuilder.build());
        Glide.get(imageParam.getContext()).register(GlideUrl.class, InputStream.class, factory);
        if (imageParam.isClearCache()) {
            Glide.get(imageParam.getContext()).clearDiskCache();
            Glide.get(imageParam.getContext()).clearMemory();
        }
        RequestManager manager = Glide.with(imageParam.getContext());
        BitmapTypeRequest glideManager;
        if (imageParam.getImageType() == ImageParam.ImageType.URI) {
            glideManager = manager.load(Uri.parse(imageParam.getUrl())).asBitmap();
        } else if (imageParam.getImageType() == ImageParam.ImageType.FILE) {
            glideManager = manager.load(new File(Uri.parse(imageParam.getUrl()).getPath())).asBitmap();
        } else {
            glideManager = manager.load(imageParam.getUrl()).asBitmap();
        }
        if (imageParam.getLoadingThumbnail() != -1)
            glideManager.placeholder(imageParam.getLoadingThumbnail());
        if (imageParam.getErrorThumbnail() != -1)
            glideManager.error(imageParam.getErrorThumbnail());
        if (imageParam.isDisableCache()) {
            glideManager.skipMemoryCache(true);
            glideManager.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (imageParam.getHeight() > 0 && imageParam.getWidth() > 0) {
            glideManager.override(imageParam.getWidth(), imageParam.getHeight());
        }
        if (imageParam.getProgressBar() != null) {
            imageParam.getProgressBar().setVisibility(View.VISIBLE);
        }
        glideManager.listener(new GlideRequestListener(imageParam));
        if (imageParam.getTransformation() != null) {
            glideManager.transform(imageParam.getTransformation());
        }
        if (imageParam.getImageView() != null)
            glideManager.into(imageParam.getImageView());

    }

    private class GlideRequestListener implements RequestListener<String, Bitmap> {
        private ImageParam imageParam;

        private GlideRequestListener(ImageParam imageParam) {
            this.imageParam = imageParam;
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean onException(Exception e, String model, com.bumptech.glide.request.target.Target<Bitmap> target, boolean isFirstResource) {
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.INVISIBLE);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, String model, com.bumptech.glide.request.target.Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.INVISIBLE);
            }
            if (imageParam.getCallback() != null) {
                imageParam.getCallback().onBitmapReceived(resource, imageParam.getTaskId());
            }
            return false;
        }
    }
}

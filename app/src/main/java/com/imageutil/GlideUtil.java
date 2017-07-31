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
                if (imageParam.header != null && !imageParam.header.isEmpty()) {
                    for (Map.Entry<String, String> entry : imageParam.header.entrySet()) {
                        request = request.newBuilder().addHeader(entry.getKey(), entry.getValue()).build();
                    }
                }
                return chain.proceed(request);
            }
        });
        mOkHttpClientBuilder.addInterceptor(mInterceptor);
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(mOkHttpClientBuilder.build());
        Glide.get(imageParam.context).register(GlideUrl.class, InputStream.class, factory);
        if (imageParam.clearCache) {
            Glide.get(imageParam.context).clearDiskCache();
            Glide.get(imageParam.context).clearMemory();
        }
        RequestManager manager = Glide.with(imageParam.context);
        BitmapTypeRequest glideManager;
        if (imageParam.imageType == ImageParam.ImageType.URI) {
            glideManager = manager.load(Uri.parse(imageParam.url)).asBitmap();
        } else if (imageParam.imageType == ImageParam.ImageType.FILE) {
            glideManager = manager.load(new File(Uri.parse(imageParam.url).getPath())).asBitmap();
        } else {
            glideManager = manager.load(imageParam.url).asBitmap();
        }
        if (imageParam.loadingThumbnail != -1)
            glideManager.placeholder(imageParam.loadingThumbnail);
        if (imageParam.errorThumbnail != -1)
            glideManager.error(imageParam.errorThumbnail);
        if (imageParam.disableCache) {
            glideManager.skipMemoryCache(true);
            glideManager.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (imageParam.height > 0 && imageParam.width > 0) {
            glideManager.override(imageParam.width, imageParam.height);
        }
        if (imageParam.progressBar != null) {
            imageParam.progressBar.setVisibility(View.VISIBLE);
        }
        glideManager.listener(new GlideRequestListener(imageParam));
        if (imageParam.transformation != null) {
            glideManager.transform(imageParam.transformation);
        }
        glideManager.into(imageParam.imageView);

    }

    private class GlideRequestListener implements RequestListener<String, Bitmap> {
        private ImageParam imageParam;

        private GlideRequestListener(ImageParam imageParam) {
            this.imageParam = imageParam;
            if (imageParam.progressBar != null) {
                imageParam.progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean onException(Exception e, String model, com.bumptech.glide.request.target.Target<Bitmap> target, boolean isFirstResource) {
            if (imageParam.progressBar != null) {
                imageParam.progressBar.setVisibility(View.INVISIBLE);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, String model, com.bumptech.glide.request.target.Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (imageParam.progressBar != null) {
                imageParam.progressBar.setVisibility(View.INVISIBLE);
            }
            return false;
        }
    }
}

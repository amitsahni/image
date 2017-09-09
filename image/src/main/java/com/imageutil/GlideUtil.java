package com.imageutil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

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
    private HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor();
    public static boolean LOG_ENABLED = false;

    /**
     * Sets image.
     *
     * @param imageParam the image param
     */
    public void setImage(final ImageParam imageParam) {
        if (ImageConfiguration.isDebug()) {
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            mInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        mOkHttpClientBuilder.connectTimeout(ImageConfiguration.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(ImageConfiguration.getReadTimeOut(), TimeUnit.MILLISECONDS);
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
        Glide.get(imageParam.getContext()).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
        if (imageParam.isClearCache()) {
            Glide.get(imageParam.getContext()).clearMemory();
            new Thread(new Runnable() {
                public void run() {
                    // your code goes here...
                    Glide.get(imageParam.getContext()).clearDiskCache();
                }
            }).start();
        }
        RequestBuilder<Bitmap> requestBuilder;
        RequestOptions options = new RequestOptions();
        RequestManager manager = Glide.with(imageParam.getContext());
        if (imageParam.getImageType() == ImageParam.ImageType.URI) {
            requestBuilder = manager.asBitmap().load(Uri.parse(imageParam.getUrl()));
        } else if (imageParam.getImageType() == ImageParam.ImageType.FILE) {
            requestBuilder = manager.asBitmap().load(new File(Uri.parse(imageParam.getUrl()).getPath()));
        } else {
            requestBuilder = manager.asBitmap().load(imageParam.getUrl());
        }
        if (imageParam.getLoadingThumbnail() != -1)
            options.placeholder(imageParam.getLoadingThumbnail());
        if (imageParam.getErrorThumbnail() != -1)
            options.error(imageParam.getErrorThumbnail());
        if (imageParam.isDisableCache()) {
            options.skipMemoryCache(true);
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (imageParam.getHeight() > 0 && imageParam.getWidth() > 0) {
            options.override(imageParam.getWidth(), imageParam.getHeight());
        }
        if (imageParam.getProgressBar() != null) {
            imageParam.getProgressBar().setVisibility(View.VISIBLE);
        }
        if (imageParam.getTransformation() != null) {
            options.transform(imageParam.getTransformation());
        }
        requestBuilder.apply(options);
        if (imageParam.getImageView() != null) {
            requestBuilder.listener(new GlideRequestListener(imageParam));
            if (imageParam.getScaleType() != null) {
                imageParam.getImageView().setScaleType(imageParam.getScaleType());
            }
            requestBuilder.into(imageParam.getImageView());
        } else {
            requestBuilder.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (imageParam.getCallback() != null
                            && imageParam.isNeedBitmap()) {
                        imageParam.getCallback().onBitmapReceived(resource, imageParam.getTaskId());
                    }
                }
            });
        }

    }

    private class GlideRequestListener implements RequestListener<Bitmap> {
        private ImageParam imageParam;

        private GlideRequestListener(ImageParam imageParam) {
            this.imageParam = imageParam;
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.INVISIBLE);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            if (imageParam.getProgressBar() != null) {
                imageParam.getProgressBar().setVisibility(View.INVISIBLE);
            }
            if (imageParam.getCallback() != null
                    && imageParam.isNeedBitmap()) {
                imageParam.getCallback().onBitmapReceived(resource, imageParam.getTaskId());
            }
            return false;
        }
    }
}


package com.imageutil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imageutil.listener.DownloadListener;
import com.imageutil.listener.LoaderListener;
import com.imageutil.listener.ProgressListener;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by clickapps on 19/1/18.
 */
@SuppressWarnings({"unchecked", "CheckResult"})
public class RequestBuilder {


    @SuppressWarnings("CheckResult")
    public static class GalleryBuilder<T extends GalleryBuilder> implements ImageProperty<T> {
        ImageParam param;

        public GalleryBuilder(ImageParam param) {
            this.param = param;
        }

        @Override
        public T thumbnail(int loadingThumb, int errorThumb) {
            param.loadingThumbnail = loadingThumb;
            param.errorThumbnail = errorThumb;
            return (T) this;
        }

        @Override
        public T header(@NonNull Map<String, String> headers) {
            param.header = headers;
            return (T) this;
        }

        @Override
        public T resize(int height, int width) {
            param.height = height;
            param.width = width;
            return (T) this;
        }

        @Override
        public T cache(boolean isCache) {
            param.disableCache = isCache;
            return (T) this;
        }

        @Override
        public T transform(Transformation<Bitmap> transformations) {
            param.transformation = transformations;
            return (T) this;
        }

        @Override
        public T progressListener(@NonNull ProgressListener listener) {
            param.progressListener = listener;
            return (T) this;
        }

        @Override
        public T loaderListener(@NonNull LoaderListener listener) {
            param.loaderListener = listener;
            return (T) this;
        }

        public T scaleType(@NonNull ImageView.ScaleType scaleType) {
            param.scaleType = scaleType;
            return (T) this;
        }

        public void build() {
            if (param.context == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            if (!param.header.isEmpty()) {
                okHttpClient = glideUtil.addHeader(param.header);
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.context).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.context);
            requestBuilder = manager.asBitmap().load(Uri.parse(param.url));
            if (param.loadingThumbnail != -1) {
                options.placeholder(param.loadingThumbnail);
            } else {
                options.placeholder(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.errorThumbnail != -1) {
                options.error(param.errorThumbnail);
            } else {
                options.error(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.disableCache) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (param.height > 0 && param.width > 0) {
                options.override(param.width, param.height);
            }
            if (param.transformation != null) {
                options.transform(param.transformation);
            }
            if (param.scaleType != null) {
                param.imageView.setScaleType(param.scaleType);
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.imageView);
        }
    }

    public static class FileBuilder extends GalleryBuilder<FileBuilder> {

        public FileBuilder(ImageParam param) {
            super(param);
        }

        public void build() {
            if (param.context == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            if (!param.header.isEmpty()) {
                okHttpClient = glideUtil.addHeader(param.header);
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.context).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.context);
            requestBuilder = manager.asBitmap().load(new File(param.url));
            if (param.loadingThumbnail != -1) {
                options.placeholder(param.loadingThumbnail);
            } else {
                options.placeholder(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.errorThumbnail != -1) {
                options.error(param.errorThumbnail);
            } else {
                options.error(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.disableCache) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (param.height > 0 && param.width > 0) {
                options.override(param.width, param.height);
            }
            if (param.transformation != null) {
                options.transform(param.transformation);
            }
            if (param.scaleType != null) {
                param.imageView.setScaleType(param.scaleType);
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.imageView);
        }
    }

    public static class UrlBuilder extends GalleryBuilder<UrlBuilder> {

        public UrlBuilder(ImageParam param) {
            super(param);
        }

        public void build() {
            if (param.context == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            if (!param.header.isEmpty()) {
                okHttpClient = glideUtil.addHeader(param.header);
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.context).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.context);
            requestBuilder = manager.asBitmap().load(param.url);
            if (param.loadingThumbnail != -1) {
                options.placeholder(param.loadingThumbnail);
            } else {
                options.placeholder(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.errorThumbnail != -1) {
                options.error(param.errorThumbnail);
            } else {
                options.error(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.disableCache) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (param.height > 0 && param.width > 0) {
                options.override(param.width, param.height);
            }
            if (param.transformation != null) {
                options.transform(param.transformation);
            }
            if (param.scaleType != null) {
                param.imageView.setScaleType(param.scaleType);
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.imageView);
        }
    }

    public static class DownloadBuilder extends GalleryBuilder<DownloadBuilder> {

        public DownloadBuilder(ImageParam param) {
            super(param);
        }

        public DownloadBuilder taskId(int taskId) {
            param.taskId = taskId;
            return this;
        }

        public DownloadBuilder downloadListener(@NonNull DownloadListener listener) {
            param.downloadListener = listener;
            return this;
        }

        public void build() {
            if (param.context == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            if (!param.header.isEmpty()) {
                okHttpClient = glideUtil.addHeader(param.header);
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.context).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.context);
            requestBuilder = manager.asBitmap().load(param.url);
            if (param.loadingThumbnail != -1) {
                options.placeholder(param.loadingThumbnail);
            } else {
                options.placeholder(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.errorThumbnail != -1) {
                options.error(param.errorThumbnail);
            } else {
                options.error(ContextCompat.getDrawable(param.context, android.R.color.holo_red_dark));
            }
            if (param.disableCache) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (param.height > 0 && param.width > 0) {
                options.override(param.width, param.height);
            }
            if (param.transformation != null) {
                options.transform(param.transformation);
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (param.downloadListener != null) {
                        param.downloadListener.download(resource, param.taskId);
                    }
                }
            });

        }
    }
}

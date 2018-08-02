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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by clickapps on 19/1/18.
 */
@SuppressWarnings({"unchecked", "CheckResult"})
public class RequestBuilder {


    @SuppressWarnings("CheckResult")
    public static class GalleryBuilder implements ImageProperty<GalleryBuilder> {
        ImageParam param;

        public GalleryBuilder(ImageParam param) {
            this.param = param;
        }

        @Override
        public GalleryBuilder thumbnail(int loadingThumb, int errorThumb) {
            param.setLoadingThumbnail(loadingThumb);
            param.setErrorThumbnail(errorThumb);
            return this;
        }

        @Override
        public GalleryBuilder resize(int height, int width) {
            param.setHeight(height);
            param.setWidth(width);
            return this;
        }

        @Override
        public GalleryBuilder cache(boolean isCache) {
            param.setDisableCache(isCache);
            return this;
        }

        @Override
        public GalleryBuilder tasKId(int taskId) {
            param.setTaskId(taskId);
            return this;
        }

        @Override
        public GalleryBuilder transform(Transformation<Bitmap> transformations) {
            param.setTransformation(transformations);
            return this;
        }

        @Override
        public GalleryBuilder progressListener(@NonNull ProgressListener listener) {
            param.setProgressListener(listener);
            return this;
        }

        @Override
        public GalleryBuilder loaderListener(@NonNull LoaderListener listener) {
            param.setLoaderListener(listener);
            return this;
        }

        public GalleryBuilder scaleType(@NonNull ImageView.ScaleType scaleType) {
            param.setScaleType(scaleType);
            return this;
        }

        public void build() {
            if (param.getContext() == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.getContext()).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.getContext());
            requestBuilder = manager.asBitmap().load(Uri.parse(param.getUrl()));
            if (param.getLoadingThumbnail() != -1) {
                options.placeholder(param.getLoadingThumbnail());
            } else {
                options.placeholder(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getErrorThumbnail() != -1) {
                options.error(param.getErrorThumbnail());
            } else {
                options.error(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getDisableCache()) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                options.signature(new CacheKey(1));
            }
            if (param.getHeight() > 0 && param.getWidth() > 0) {
                options.override(param.getWidth(), param.getHeight());
            }
            if (param.getTransformation() != null) {
                options.transform(param.getTransformation());
            }
            if (param.getScaleType() != null) {
                param.getImageView().setScaleType(param.getScaleType());
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.getImageView());
        }
    }

    public static class FileBuilder extends GalleryBuilder {

        public FileBuilder(ImageParam param) {
            super(param);
        }

        public void build() {
            if (param.getContext() == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.getContext()).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.getContext());
            requestBuilder = manager.asBitmap().load(new File(param.getUrl()));
            if (param.getLoadingThumbnail() != -1) {
                options.placeholder(param.getLoadingThumbnail());
            } else {
                options.placeholder(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getErrorThumbnail() != -1) {
                options.error(param.getErrorThumbnail());
            } else {
                options.error(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getDisableCache()) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (param.getHeight() > 0 && param.getWidth() > 0) {
                options.override(param.getWidth(), param.getHeight());
            }
            if (param.getTransformation() != null) {
                options.transform(param.getTransformation());
            }
            if (param.getScaleType() != null) {
                param.getImageView().setScaleType(param.getScaleType());
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.getImageView());
        }
    }

    public static class UrlBuilder extends GalleryBuilder {

        public UrlBuilder(ImageParam param) {
            super(param);
        }

        public UrlBuilder header(@NonNull Map<String, String> headers) {
            param.setHeader(headers);
            return this;
        }

        public void build() {
            if (param.getContext() == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            GlideUrl glideUrl;
            if (!param.getHeader().isEmpty()) {
                glideUrl = new GlideUrl(param.getUrl(), glideUtil.addHeader(param.getHeader()));
            } else {
                glideUrl = new GlideUrl(param.getUrl());
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.getContext()).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.getContext());
            requestBuilder = manager.asBitmap().load(glideUrl);
            if (param.getLoadingThumbnail() != -1) {
                options.placeholder(param.getLoadingThumbnail());
            } else {
                options.placeholder(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getErrorThumbnail() != -1) {
                options.error(param.getErrorThumbnail());
            } else {
                options.error(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getDisableCache()) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                options.signature(new CacheKey(1));
            }

            if (param.getHeight() > 0 && param.getWidth() > 0) {
                options.override(param.getWidth(), param.getHeight());
            }
            if (param.getTransformation() != null) {
                options.transform(param.getTransformation());
            }
            if (param.getScaleType() != null) {
                param.getImageView().setScaleType(param.getScaleType());
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(param.getImageView());
        }
    }

    public static class DownloadBuilder extends UrlBuilder {

        public DownloadBuilder(ImageParam param) {
            super(param);
        }

        public DownloadBuilder downloadListener(@NonNull DownloadListener listener) {
            param.setDownloadListener(listener);
            return this;
        }

        public DownloadBuilder saveTo(@NonNull File file) {
            param.setFile(file);
            return this;
        }

        public void build() {
            if (param.getContext() == null) return;
            GlideUtil glideUtil = GlideUtil.get();
            OkHttpClient okHttpClient = glideUtil.getDefaultOkHttpClient(param);
            GlideUrl glideUrl;
            if (!param.getHeader().isEmpty()) {
                glideUrl = new GlideUrl(param.getUrl(), glideUtil.addHeader(param.getHeader()));
            } else {
                glideUrl = new GlideUrl(param.getUrl());
            }
            OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
            Glide.get(param.getContext()).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
            com.bumptech.glide.RequestBuilder<Bitmap> requestBuilder;
            RequestOptions options = new RequestOptions();
            RequestManager manager = Glide.with(param.getContext());
            requestBuilder = manager.asBitmap().load(glideUrl);
            if (param.getLoadingThumbnail() != -1) {
                options.placeholder(param.getLoadingThumbnail());
            } else {
                options.placeholder(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getErrorThumbnail() != -1) {
                options.error(param.getErrorThumbnail());
            } else {
                options.error(ContextCompat.getDrawable(param.getContext(), android.R.color.holo_red_dark));
            }
            if (param.getDisableCache()) {
                options.skipMemoryCache(true);
                options.diskCacheStrategy(DiskCacheStrategy.NONE);
                options.signature(new CacheKey(1));
            }
            if (param.getHeight() > 0 && param.getWidth() > 0) {
                options.override(param.getWidth(), param.getHeight());
            }
            if (param.getTransformation() != null) {
                options.transform(param.getTransformation());
            }
            requestBuilder.apply(options);
            requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
            requestBuilder.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (param.getDownloadListener() != null) {
                        param.getDownloadListener().download(resource, param.getTaskId());
                        if (param.getFile() != null) {
                            FileOutputStream out = null;
                            try {
                                out = new FileOutputStream(param.getFile());
                                resource.compress(Bitmap.CompressFormat.PNG, 100, out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (out != null) {
                                        out.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });

        }
    }
}

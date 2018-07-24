package com.imageutil.di.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.imageutil.GlideUtil;
import com.imageutil.ImageParam;
import com.imageutil.di.qualifier.ApplicationContext;

import java.io.InputStream;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by clickapps on 2/7/18.
 */
@Module(includes = NetworkModule.class)
public class ImageModule {


    @Provides
    public RequestOptions requestOptions(ImageParam param) {
        RequestOptions options = new RequestOptions();
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
        return options;
    }

    public RequestManager requestManager(@ApplicationContext Context context, OkHttpClient okHttpClient) {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
        Glide.get(context).getRegistry().replace(GlideUrl.class, InputStream.class, factory);
        return Glide.with(context);
    }

    public void builder(RequestManager manager, RequestOptions requestOptions) {
//        RequestBuilder<Bitmap> requestBuilder = manager.asBitmap().load(Uri.parse(param.getUrl()));
//        requestBuilder.apply(requestOptions);
//        requestBuilder.listener(new GlideUtil.GlideRequestListener(param));
//        requestBuilder.into(param.getImageView());
    }

    public void setImage(){

    }


}

package com.imageutil;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


/**
 * The type Glide util.
 */
public class GlideUtil {
    private static volatile GlideUtil sGlideUtil;

    /**
     * Get web connect.
     *
     * @return the web connect
     */

    static GlideUtil get() {
        if (sGlideUtil == null) {
            synchronized (GlideUtil.class) {
                if (sGlideUtil == null) {
                    sGlideUtil = new GlideUtil();
                }
            }
        }
        return sGlideUtil;
    }

    /*************************************/
    OkHttpClient getDefaultOkHttpClient(@NonNull final ImageParam imageParam) {
        OkHttpClient okHttpClient = ImageConfiguration.getOkHttpClient();
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(ImageConfiguration.sCONNECT_TIME_OUT, TimeUnit.SECONDS);
            builder.writeTimeout(ImageConfiguration.sCONNECT_TIME_OUT, TimeUnit.SECONDS);
            builder.readTimeout(ImageConfiguration.sREAD_TIME_OUT, TimeUnit.SECONDS);
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequestsPerHost(2);
            dispatcher.setMaxRequests(10);
            builder.dispatcher(dispatcher);
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(ImageConfiguration.sIS_DEBUG ?
                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            builder.addInterceptor(interceptor);
            okHttpClient = builder.build();
        }
        okHttpClient = okHttpClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .body(new GlideUtil.ProgressResponseBody(response.body(), imageParam))
                        .build();
            }
        }).build();
        return okHttpClient;
    }

    LazyHeaders addHeader(@NonNull final Map<String, String> header) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    /*************************************/
    public class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ImageParam imageParam;
        private BufferedSource bufferedSource;

        ProgressResponseBody(ResponseBody responseBody, ImageParam imageParam) {
            this.responseBody = responseBody;
            this.imageParam = imageParam;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    long length = responseBody.contentLength();
                    int progress = 0;
                    try {
                        progress = (int) ((100 * totalBytesRead) / length);
                    } catch (Exception e) {
                        if (ImageConfiguration.isDebug()) {
                            Log.e(getClass().getSimpleName(), "Exception = " + e.getMessage());
                        }
                    }
                    if (imageParam.getProgressListener() != null)
                        imageParam.getProgressListener().update(totalBytesRead, length, progress);
                    return bytesRead;
                }
            };
        }
    }

    static class GlideRequestListener implements RequestListener<Bitmap> {
        private ImageParam imageParam;

        GlideRequestListener(ImageParam imageParam) {
            this.imageParam = imageParam;
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(true);
            }
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(false);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(false);
            }
            return false;
        }
    }


    static class GlideFileRequestListener implements RequestListener<File> {
        private ImageParam imageParam;

        GlideFileRequestListener(ImageParam imageParam) {
            this.imageParam = imageParam;
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(true);
            }
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(false);
            }
            return false;
        }

        @Override
        public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
            if (imageParam.getLoaderListener() != null) {
                imageParam.getLoaderListener().loader(false);
            }
            return false;
        }
    }
}


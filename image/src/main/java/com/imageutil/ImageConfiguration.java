package com.imageutil;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by amit on 9/9/17.
 */

public class ImageConfiguration implements Serializable {

    static long sCONNECT_TIME_OUT = 10 * 1000L;
    static long sREAD_TIME_OUT = 20 * 1000L;
    static boolean sIS_DEBUG = true;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Dispatcher dispatcher = new Dispatcher();

    public static long getConnectTimeOut() {
        return sCONNECT_TIME_OUT;
    }

    public static long getReadTimeOut() {
        return sREAD_TIME_OUT;
    }

    public static boolean isDebug() {
        return sIS_DEBUG;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static class Builder {
        private long connectTimeOut = 10 * 1000L;
        private long readTimeOut = 20 * 1000L;
        private boolean isDebug = true;

        public Builder() {
        }

        public Builder timeOut(long connectTimeOut, long readTimeOut) {
            this.connectTimeOut = connectTimeOut;
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder isDebug(boolean debuggable) {
            this.isDebug = debuggable;
            return this;
        }

        public void config() {
            ImageConfiguration.sCONNECT_TIME_OUT = connectTimeOut;
            ImageConfiguration.sREAD_TIME_OUT = readTimeOut;
            ImageConfiguration.sIS_DEBUG = isDebug;
            dispatcher.setMaxRequestsPerHost(2);
            dispatcher.setMaxRequests(10);
            okhttp3.logging.HttpLoggingInterceptor interceptor = new okhttp3.logging.HttpLoggingInterceptor();
            interceptor.setLevel(isDebug ?
                    okhttp3.logging.HttpLoggingInterceptor.Level.BODY : okhttp3.logging.HttpLoggingInterceptor.Level.NONE);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient
                    .newBuilder()
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .dispatcher(dispatcher)
                    .addInterceptor(interceptor)
                    .build();
        }

    }


}
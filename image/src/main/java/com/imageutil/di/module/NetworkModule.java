package com.imageutil.di.module;

import android.content.Context;
import android.util.Log;

import com.imageutil.ImageConfiguration;
import com.imageutil.di.qualifier.ApplicationContext;
import com.imageutil.di.scope.ImageUtilScope;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by clickapps on 2/7/18.
 */
@Module
public class NetworkModule {

    @Provides
    @ImageUtilScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(NetworkModule.class.getSimpleName(), message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        interceptor.setLevel(ImageConfiguration.isDebug() ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    @ImageUtilScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB Cahe
    }

    @Provides
    @ImageUtilScope
    public File cacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @ImageUtilScope
    public Dispatcher dispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(2);
        dispatcher.setMaxRequests(10);
        return dispatcher;
    }

    @Provides
    @ImageUtilScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                     Cache cache, Dispatcher dispatcher) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ImageConfiguration.getConnectTimeOut(), TimeUnit.SECONDS);
        builder.writeTimeout(ImageConfiguration.getConnectTimeOut(), TimeUnit.SECONDS);
        builder.readTimeout(ImageConfiguration.getReadTimeOut(), TimeUnit.SECONDS);
        builder.dispatcher(dispatcher);
        builder.cache(cache);
        builder.addInterceptor(loggingInterceptor);
//        builder.addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response response = chain.proceed(request);
//                return response.newBuilder()
//                        .body(new GlideUtil.ProgressResponseBody(response.body(), imageParam))
//                        .build();
//            }
//        });
        return builder.build();
    }
}

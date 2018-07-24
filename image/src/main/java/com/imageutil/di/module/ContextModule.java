package com.imageutil.di.module;

import android.content.Context;

import com.imageutil.di.qualifier.ApplicationContext;
import com.imageutil.di.scope.ImageUtilScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by clickapps on 2/7/18.
 */
@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ImageUtilScope
    @ApplicationContext
    public Context context() {
        return context;
    }
}

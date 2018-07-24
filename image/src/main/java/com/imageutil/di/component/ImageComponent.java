package com.imageutil.di.component;

import com.imageutil.ImageConfiguration;
import com.imageutil.RequestBuilder;
import com.imageutil.di.module.ImageModule;
import com.imageutil.di.module.NetworkModule;
import com.imageutil.di.scope.ImageUtilScope;

import dagger.Component;

/**
 * Created by clickapps on 2/7/18.
 */
@ImageUtilScope
@Component(modules = {NetworkModule.class, ImageModule.class})
public interface ImageComponent {

    void inject(ImageConfiguration imageConfiguration);

    void inject(RequestBuilder.GalleryBuilder galleryBuilder);

}

package com.imageutil;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by clickapps on 19/1/18.
 */

public class Builder {
    private ImageParam param;

    public Builder(@NonNull Context context) {
        param = new ImageParam();
        param.context = context;
    }

    public RequestBuilder.GalleryBuilder gallery(@NonNull Uri uri, @NonNull ImageView imageView) {
        param.imageType = ImageParam.ImageType.URI;
        param.url = uri.toString();
        param.imageView = imageView;
        return new RequestBuilder.GalleryBuilder(param);
    }

    public RequestBuilder.FileBuilder file(@NonNull File file, @NonNull ImageView imageView) {
        param.imageType = ImageParam.ImageType.URI;
        param.url = file.toString();
        param.imageView = imageView;
        return new RequestBuilder.FileBuilder(param);
    }

    public RequestBuilder.UrlBuilder url(@NonNull String url, @NonNull ImageView imageView) {
        param.imageType = ImageParam.ImageType.URL;
        param.url = url;
        param.imageView = imageView;
        return new RequestBuilder.UrlBuilder(param);
    }

    public RequestBuilder.DownloadBuilder download(@NonNull String url) {
        param.imageType = ImageParam.ImageType.DOWNLOAD;
        param.url = url;
        return new RequestBuilder.DownloadBuilder(param);
    }


}

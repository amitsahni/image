package com.imageutil

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView

import java.io.File

/**
 * Created by clickapps on 19/1/18.
 */

class Builder(context: Context) {
    private val param: ImageParam

    init {
        param = ImageParam()
        param.context = context
    }

    fun gallery(uri: Uri?, imageView: ImageView?): RequestBuilder.GalleryBuilder {
        param.imageType = ImageParam.ImageType.URI
        param.url = uri.toString()
        if (TextUtils.isEmpty(uri.toString())) {
            param.url = "Not Valid Uri"
        }
        param.imageView = imageView
        return RequestBuilder.GalleryBuilder(param)
    }

    fun file(file: File?, imageView: ImageView?): RequestBuilder.FileBuilder {
        param.imageType = ImageParam.ImageType.FILE
        param.url = file.toString()
        if (TextUtils.isEmpty(file.toString())) {
            param.url = "Not Valid File"
        }
        param.imageView = imageView
        return RequestBuilder.FileBuilder(param)
    }

    fun url(url: String?, imageView: ImageView?): RequestBuilder.UrlBuilder {
        param.imageType = ImageParam.ImageType.URL
        param.url = url
        if (TextUtils.isEmpty(url)) {
            param.url = "emptyUrl"
        }
        param.imageView = imageView
        return RequestBuilder.UrlBuilder(param)
    }

    fun download(url: String?): RequestBuilder.DownloadBuilder {
        param.imageType = ImageParam.ImageType.DOWNLOAD
        param.url = url
        if (TextUtils.isEmpty(url)) {
            param.url = "empty download Url"
        }
        return RequestBuilder.DownloadBuilder(param)
    }


}

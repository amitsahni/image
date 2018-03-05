package com.imageutil

import android.content.Context
import android.net.Uri
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

    fun gallery(uri: Uri, imageView: ImageView): RequestBuilder.GalleryBuilder {
        param.imageType = ImageParam.ImageType.URI
        param.url = uri.toString()
        param.imageView = imageView
        return RequestBuilder.GalleryBuilder(param)
    }

    fun file(file: File, imageView: ImageView): RequestBuilder.FileBuilder {
        param.imageType = ImageParam.ImageType.URI
        param.url = file.toString()
        param.imageView = imageView
        return RequestBuilder.FileBuilder(param)
    }

    fun url(url: String, imageView: ImageView): RequestBuilder.UrlBuilder {
        param.imageType = ImageParam.ImageType.URL
        param.url = url
        param.imageView = imageView
        return RequestBuilder.UrlBuilder(param)
    }

    fun download(url: String): RequestBuilder.DownloadBuilder {
        param.imageType = ImageParam.ImageType.DOWNLOAD
        param.url = url
        return RequestBuilder.DownloadBuilder(param)
    }


}

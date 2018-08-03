package com.imageutil

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.imageutil.listener.DownloadListener
import com.imageutil.listener.LoaderListener
import com.imageutil.listener.ProgressListener
import java.io.File
import java.util.*


/**
 * The type Image param.
 */
class ImageParam {
    var context: Context? = null
    var progressListener: ProgressListener? = null
    var loaderListener: LoaderListener? = null
    var downloadListener: DownloadListener? = null
    var url: String? = null

    var imageType = ImageType.URL

    var loadingThumbnail = -1
    var errorThumbnail = -1
    var taskId = -1
    var height: Int = 0
    var width: Int = 0

    var disableCache = false
    var latestOnly = false

    var header: Map<String, String> = LinkedHashMap()

    var imageView: ImageView? = null

    var scaleType: ImageView.ScaleType? = null

    var config: Bitmap.Config = Bitmap.Config.RGB_565

    var transformation: Transformation<Bitmap>? = null

    var file: File? = null

    var compressedPercentage = -1f

    enum class ImageType {
        URL,
        URI,
        FILE,
        DOWNLOAD
    }


}

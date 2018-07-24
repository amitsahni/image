package com.imageutil.listener

import android.graphics.Bitmap

/**
 * Created by clickapps on 19/1/18.
 */
@FunctionalInterface
interface DownloadListener {
    fun download(bitmap: Bitmap, taskId: Int)
}

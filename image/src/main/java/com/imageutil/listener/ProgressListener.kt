package com.imageutil.listener

/**
 * Created by clickapps on 19/1/18.
 */

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, progress: Int)
}

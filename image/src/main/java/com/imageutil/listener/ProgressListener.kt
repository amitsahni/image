package com.imageutil.listener

/**
 * Created by clickapps on 19/1/18.
 */
@FunctionalInterface
interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, progress: Int)
}

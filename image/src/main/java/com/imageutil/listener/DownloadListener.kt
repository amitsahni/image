package com.imageutil.listener

import java.io.File

/**
 * Created by clickapps on 19/1/18.
 */
@FunctionalInterface
interface DownloadListener {
    fun download(file: File, taskId: Int)
}

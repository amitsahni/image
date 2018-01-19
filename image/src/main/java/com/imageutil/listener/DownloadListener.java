package com.imageutil.listener;

import android.graphics.Bitmap;

/**
 * Created by clickapps on 19/1/18.
 */

public interface DownloadListener {
    void download(Bitmap bitmap, int taskId);
}

package com.imageutil.listener;

/**
 * Created by clickapps on 19/1/18.
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength);
}

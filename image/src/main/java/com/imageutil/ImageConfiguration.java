package com.imageutil;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by amit on 9/9/17.
 */

public class ImageConfiguration implements Serializable {

    private static long sCONNECT_TIME_OUT = 10 * 1000L;
    private static long sREAD_TIME_OUT = 20 * 1000L;
    private static boolean sIS_DEBUG = true;

    public static long getConnectTimeOut() {
        return sCONNECT_TIME_OUT;
    }

    public static long getReadTimeOut() {
        return sREAD_TIME_OUT;
    }

    public static boolean isDebug() {
        return sIS_DEBUG;
    }

    public static class Builder {
        private long connectTimeOut = 10 * 1000L;
        private long readTimeOut = 20 * 1000L;
        private boolean isDebug = true;

        public Builder() {
        }

        public Builder timeOut(long connectTimeOut, long readTimeOut) {
            this.connectTimeOut = connectTimeOut;
            this.readTimeOut = readTimeOut;
            return this;
        }

        public Builder isDebug(boolean debuggable) {
            this.isDebug = debuggable;
            return this;
        }

        public void config() {
            ImageConfiguration.sCONNECT_TIME_OUT = connectTimeOut;
            ImageConfiguration.sREAD_TIME_OUT = readTimeOut;
            ImageConfiguration.sIS_DEBUG = isDebug;

        }

    }
}
package com.imageutil;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.model.GlideUrl;

import java.security.MessageDigest;

/**
 * Created by clickapps on 2/8/18.
 */

public class CacheKey extends GlideUrl {

    private int isCache = 0;
    private String url = "";

    public CacheKey(int isCache, String url) {
        super(url);
    }

//    public CacheKey(int isCache, String url) {
//        this.isCache = isCache;
//        this.url = url;
//    }

//    @Override
//    public String toString() {
//        return "CacheKey{"
//                + "object=" + isCache
//                + '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof CacheKey) {
//            CacheKey other = (CacheKey) o;
//            return isCache == other.isCache;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return isCache;
//    }

//    @Override
//    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//        messageDigest.update(String.valueOf(url).getBytes(CHARSET));
//    }
}

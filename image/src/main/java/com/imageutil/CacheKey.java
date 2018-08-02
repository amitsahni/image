package com.imageutil;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Key;

import java.security.MessageDigest;

/**
 * Created by clickapps on 2/8/18.
 */

public class CacheKey implements Key {

    private int isCache = 0;

    public CacheKey(int isCache) {
        this.isCache = isCache;
    }

    @Override
    public String toString() {
        return "CacheKey{"
                + "object=" + isCache
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CacheKey) {
            CacheKey other = (CacheKey) o;
            return isCache == other.isCache;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return isCache;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(String.valueOf(isCache).getBytes(CHARSET));
    }
}

# ImageUtils  [![](https://jitpack.io/v/amitclickapps/image-util.svg)](https://jitpack.io/#amitclickapps/image-util)
#### ImageConfiguration
```
 new ImageConfiguration.Builder()
                .timeOut(50 * 1000L, 60 * 1000L)
                .isDebug(false)
                .config();
```
#### General Use
```
ImageUtil.with(this,"url",imageView)
                .thumbnail(R.drawable.cheese_1,R.drawable.cheese_2)
                .scaleType(ImageView.ScaleType.MATRIX)
                .build();
```
### Other methods

#### callback
```
 .callback(new ImageParam.onCallback() {
                    @Override
                    public void onBitmapReceived(Bitmap bitmap, int taskId) {
                        
                    }
                })
                .needBitmap(true,100)              
```
#### ClearWholeCache
```
.clearWholeCache()                
```
#### Disable Cache & Disable Cache with Key

```
.disableCache(true) - If set then caching will be disabled
.disableCache(url) - If set for this url caching will be disabled
                
```
#### Header 
```
If want to pass header in Image loading

.headers(new LinkedHashMap<String, String>())
```
#### Bitmap Transformation
Use [Bitmap Transforation Lib](https://github.com/wasabeef/glide-transformations) 
```
.transform(bitmaptransformation)
```
#### DownloadOnly
```
ImageUtil.with(this, url)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .needBitmap(true, 1)
                .callback(new ImageParam.onCallback() {
                    @Override
                    public void onBitmapReceived(Bitmap bitmap, int taskId) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                })
                .build();
```

# ImageType
```
URL - By Default
URI - Gallery Path
File - Sdcard Path
```
##### ScaleType
```
If not passed then whatever assigned to ImageView is automatically picked up.
If passed then passed scaleType will be used.

```

Download
--------
Add the JitPack repository to your root build.gradle:

[](https://jitpack.io/v/amitclickapps/retrofit-util.svg?style=flat-square)


```groovy
	allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```
Add the Gradle dependency:
```groovy
	dependencies {
		compile 'com.github.amitclickapps:image-util:latest'
	}
```
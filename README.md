#### ImageConfiguration
```
 new ImageConfiguration.Builder()
                .timeOut(50 * 1000L, 60 * 1000L)
                .isDebug(false)
                .config();
```
#### General Use
```
ImageUtil.with(this)
                .url(url, imageView)
                .build();
```
#### ClearWholeCache
```
ImageUtil.get().clearCache(this);               
```
### Other methods

#### callback
```
 .loaderListener(new LoaderListener() {
                     @Override
                     public void loader(boolean isLoader) {
                         
                     }
                 })
  .progressListener(new ProgressListener() {
                     @Override
                     public void update(long bytesRead, long contentLength) {
                            
                        }
                    }) 
  .downloadListener(new DownloadListener() {
                      @Override
                      public void download(Bitmap bitmap, int taskId) {
                          
                      }
                  })                      
```

#### Disable Cache

```
.disableCache(true) - If set then caching will be disabled              
```
#### Header 
```
.header(new LinkedHashMap<String, String>())
```
#### Bitmap Transformation
Use [Bitmap Transforation Lib](https://github.com/wasabeef/glide-transformations) 
```
.transform(bitmaptransformation)
```
##### ScaleType
```
.scaleType(ImageView.ScaleType.CENTER_CROP)
```
##### Resize
```
 .resize(100,100)
```
##### Thumbnail
```
.thumbnail(loading,error)
```
#### Url
```
ImageUtil.with(this)
                .url(url, imageView1)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
```
#### Gallery
```
ImageUtil.with(this)
                .gallery(uri, imageView1)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
```
#### File
```
ImageUtil.with(this)
                .file(file, imageView1)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
```
#### DownloadOnly
```
ImageUtil.with(this)
                .download(url)
                .taskId(1)
                .downloadListener(new DownloadListener() {
                    @Override
                    public void download(Bitmap bitmap, int taskId) {
                        
                    }
                })
                .build();
```




Download
--------
Add the JitPack repository to your root build.gradle:

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
		compile 'com.github.amitsahni:image:1.0.1'
	}
```
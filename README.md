# ImageUtils  [![](https://jitpack.io/v/amitclickapps/image-util.svg)](https://jitpack.io/#amitclickapps/image-util)

```
ImageUtil.with(this,"url",imageView)
                .thumbnail(R.drawable.cheese_1,R.drawable.cheese_2)
                .scaleType(ImageView.ScaleType.MATRIX)
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
		compile 'com.github.amitclickapps:image-util:1.0.1'
	}
```
package test.image

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.util.Log
import android.widget.ImageView
import com.imageutil.ImageUtil
import com.imageutil.listener.LoaderListener
import com.imageutil.listener.ProgressListener
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : Activity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val url = "http://res.cloudinary.com/clickapps/image/upload/v1504245457/test/1024x1024-Wallpapers-010.jpg"
        ImageUtil.with(this)
                .url(url, imageView)
                .progressListener(object : ProgressListener {
                    override fun update(bytesRead: Long, contentLength: Long, progress: Int) {
                        Log.i(localClassName, "Byte = $bytesRead")
                        Log.i(localClassName, "contentLength = $contentLength")
                    }
                })
                .loaderListener(object : LoaderListener {
                    override fun loader(isLoader: Boolean) {
                        Log.i(localClassName, "isLoader = $isLoader")
                    }
                })
                .build()
    }



}

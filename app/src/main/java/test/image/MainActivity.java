package test.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.imageutil.ImageUtil;
import com.imageutil.listener.DownloadListener;
import com.imageutil.listener.LoaderListener;
import com.imageutil.listener.ProgressListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by clickapps on 31/8/17.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    private final String url = "http://res.cloudinary.com/clickapps/image/upload/v1504245457/test/1024x1024-Wallpapers-010.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageUtil.with(this)
                .download(url)
                .taskId(1)
                .progressListener(new ProgressListener() {
                    @Override
                    public void update(long bytesRead, long contentLength) {

                    }
                })
                .loaderListener(new LoaderListener() {
                    @Override
                    public void loader(boolean isLoader) {

                    }
                }).downloadListener(new DownloadListener() {
                    @Override
                    public void download(Bitmap bitmap, int taskId) {

                    }
                })
                .build();

        ImageUtil.with(this)
                .url(url, imageView1)
                .progressListener(new ProgressListener() {
                    @Override
                    public void update(long bytesRead, long contentLength) {

                    }
                })
                .loaderListener(new LoaderListener() {
                    @Override
                    public void loader(boolean isLoader) {

                    }
                })

                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();

        ImageUtil.with(this)
                .url(url, imageView2)
                .scaleType(ImageView.ScaleType.FIT_XY)
                .build();

        ImageUtil.with(this)
                .url(url, imageView3)
                .scaleType(ImageView.ScaleType.CENTER)
                .build();

        ImageUtil.with(this)
                .url(url, imageView4)
                .scaleType(ImageView.ScaleType.FIT_END)
                .build();

        ImageUtil.with(this)
                .url(url, imageView5)
                .scaleType(ImageView.ScaleType.MATRIX)
                .build();

        ImageUtil.with(this)
                .url(url, imageView6)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();

        ImageUtil.with(this)
                .url(url, imageView7)
                .scaleType(ImageView.ScaleType.FIT_START)
                .build();
    }

}

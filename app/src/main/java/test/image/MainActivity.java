package test.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.imageutil.ImageUtil;

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
    private final String url = "http://res.cloudinary.com/clickapp/image/upload/h_300,w_800/v1503404664/monh/staging/categories/Protein_Arabic.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageUtil.with(this, url, imageView)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
        ImageUtil.with(this, url, imageView1)
                .scaleType(ImageView.ScaleType.CENTER_CROP)
                .build();
        ImageUtil.with(this, url, imageView2)
                .scaleType(ImageView.ScaleType.FIT_XY)
                .build();

        ImageUtil.with(this, url, imageView3)
                .scaleType(ImageView.ScaleType.CENTER)
                .build();
        ImageUtil.with(this, url, imageView4)
                .scaleType(ImageView.ScaleType.FIT_END)
                .build();
        ImageUtil.with(this, url, imageView5)
                .scaleType(ImageView.ScaleType.MATRIX)
                .build();
        ImageUtil.with(this, url, imageView6)
                .scaleType(ImageView.ScaleType.FIT_CENTER)
                .build();

        ImageUtil.with(this, url, imageView7)
                .scaleType(ImageView.ScaleType.FIT_START)
                .build();
    }

}

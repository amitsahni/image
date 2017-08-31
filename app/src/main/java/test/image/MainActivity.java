package test.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.imageutil.ImageParam;
import com.imageutil.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by clickapps on 31/8/17.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    private final String url = "http://res.cloudinary.com/clickapp/image/upload/h_1440,w_2960/v1503404664/monh/staging/categories/Protein_Arabic.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageUtil.with(this, url, imageView)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
    }

}

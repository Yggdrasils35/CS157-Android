package info.androidhive.homework7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    ViewPager viewPager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(ImageActivity.this);
        setContentView(R.layout.activity_image);
        layoutInflater = getLayoutInflater();
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        addImage("res:/" + R.drawable.drawableimage);
        addImage("res:/" + R.drawable.master1200);
        addRawImage(R.drawable.wutiaowu);
        ViewAdapter adapter = new ViewAdapter();
        adapter.setDatas(pages);
        viewPager.setAdapter(adapter);
    }

    private void addImage(Uri uri) {
        SimpleDraweeView imageView = (SimpleDraweeView) layoutInflater.inflate(R.layout.activity_fresco_item, null);
        imageView.setImageURI(uri);
        imageView
                .getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        pages.add(imageView);
    }

    private void addImage(String path) {
        addImage(Uri.parse(path));
    }

    private void addRawImage(int resId){
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(resId).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)
                .build();
        SimpleDraweeView imageView = (SimpleDraweeView) layoutInflater.inflate(R.layout.activity_fresco_item, null);
        imageView
                .getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        imageView.setController(controller);
        pages.add(imageView);
    }
}
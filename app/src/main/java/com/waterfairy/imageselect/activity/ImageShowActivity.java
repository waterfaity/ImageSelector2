package com.waterfairy.imageselect.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.waterfairy.imageselect.R;
import com.waterfairy.imageselect.utils.AnimUtils;
import com.waterfairy.imageselect.utils.ConstantUtils;
import com.waterfairy.imageselect.utils.PathUtils;
import com.waterfairy.imageselect.widget.ZoomImageView;

import java.io.File;

//import com.github.chrisbanes.photoview.OnPhotoTapListener;
//import com.github.chrisbanes.photoview.PhotoView;


public class ImageShowActivity extends RootActivity {
    private boolean isVisibility = true;
    private ZoomImageView zoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_selector_activity_image_show);

        zoomImageView = findViewById(R.id.image);
        zoomImageView.setCanZoom(false);
        Intent intent = getIntent();
        //url
        String url = intent.getStringExtra(ConstantUtils.STR_URL);
        //本地地址
        String path = intent.getStringExtra(ConstantUtils.STR_PATH);
        //标题
        String title = intent.getStringExtra(ConstantUtils.STR_IMG_TITLE);  //设置标题

        TextView tVTitle = findViewById(R.id.title);
        if (!TextUtils.isEmpty(url)) {
            tVTitle.setText(TextUtils.isEmpty(title) ? PathUtils.getNameFromUrl(url) : title);

            Glide.with(this).load(url).into(zoomImageView);
        } else if (!TextUtils.isEmpty(path)) {
            tVTitle.setText(TextUtils.isEmpty(title) ? new File(path).getName() : title);

            Glide.with(this).load(new File(path)).into(zoomImageView);
        }
        final View topView = findViewById(R.id.rel_top);
        zoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisibility) {
                    topView.startAnimation(AnimUtils.getInAnim(true, false));
                } else {
                    topView.startAnimation(AnimUtils.getInAnim(true, true));
                }
                isVisibility = !isVisibility;
            }
        });
    }


    public void back(View view) {
        if (isVisibility)
            ActivityCompat.finishAfterTransition(this);
    }
}

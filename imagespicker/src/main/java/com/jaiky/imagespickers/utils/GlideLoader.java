package com.jaiky.imagespickers.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.R;

/**
 * @author xch
 * @version 1.0
 * @create_date 2017/3/9
 */

public class GlideLoader implements ImageLoader {

    private static final long serialVersionUID = 1L;

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.global_img_default)
                .fitCenter()
                .override(1000, 1000)
                .into(imageView);
    }
}

/*
 * Copyright 2020 vivekverma
 *
 * Licensed under the MIT License, (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://github.com/vivekverma007/BlurShadowImageView/blob/master/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.virtualiz.blurshadowimageview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.virtualiz.blurshadowimageview.helper.FadingImageView;
import me.virtualiz.blurshadowimageview.helper.RoundImageView;

/**
 * ================================================
 * virtualiz.me@gmail.com
 * -version：1.1
 * -created ：2019/12/02
 * Developed by：
 * Vivek Verma
 * ================================================
 */
public class BlurShadowImageView extends RelativeLayout {

    private int imageRound = dpToPx(10);
    private int shadowOffset = dpToPx(50);
    private boolean mInvalidat;

    public BlurShadowImageView(Context context) {
        this(context, null);
    }
    public BlurShadowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BlurShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }
    public static int dpToPx(int dp){
        return (int) (dp* Resources.getSystem().getDisplayMetrics().density);
    }
    private void initView(Context context, AttributeSet attrs) {

        setGravity(Gravity.CENTER);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        int imageresource = -1;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BlurShadowImageView);

            if (a.hasValue(R.styleable.BlurShadowImageView_v_imageSrc)) {
                imageresource = a.getResourceId(R.styleable.BlurShadowImageView_v_imageSrc, -1);
            }
            imageRound = a.getDimensionPixelSize(R.styleable.BlurShadowImageView_v_imageRound, imageRound);
            shadowOffset = a.getDimensionPixelSize(R.styleable.BlurShadowImageView_v_shadowOffset, shadowOffset);
            a.recycle();

        } else {
            float density = context.getResources().getDisplayMetrics().density;
            imageRound = (int) (imageRound * density);
            imageresource = -1;
        }

        //Layer imageView
        RoundImageView roundImageView = new RoundImageView(context);
        roundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        if (imageresource == -1) {
            roundImageView.setImageResource(android.R.color.transparent);
        } else {
            roundImageView.setImageResource(imageresource);
        }

        //Layer blurView
        FadingImageView blurImageView = new FadingImageView(context);
        blurImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Blurring techinique without renderscript --------------
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap blurredImage = BitmapFactory.decodeResource(getResources(), imageresource, options);

        //setting blurred image to fadding blur imageview ----
        blurImageView.setImageBitmap(blurredImage);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        blurImageView.setLayoutParams(lp);

        LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp2.setMargins(dpToPx(15),0,dpToPx(15),shadowOffset);
        roundImageView.setLayoutParams(lp2);

        addView(blurImageView);
        addView(roundImageView);

       getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int N = getChildCount();
                for (int i = 0; i < N; i++) {
                    N = getChildCount();
                }
                ((RoundImageView) getChildAt(1)).setRound(imageRound);
                mInvalidat = true;
            }
        });

    }
    public void setImageResource(int resId) {
        ((RoundImageView) getChildAt(1)).setImageResource(resId);
        invalidate();
        mInvalidat = true;
    }
    public void setImageDrawable(Drawable drawable) {
        ((RoundImageView) getChildAt(1)).setImageDrawable(drawable);
        invalidate();
        mInvalidat = true;
    }
    public void setImageBitmap(Bitmap bitmap) {
        ((RoundImageView) getChildAt(1)).setImageBitmap(bitmap);
        invalidate();
        mInvalidat = true;
    }
    public void setImageRadius(int radius) {
        if (radius > getChildAt(1).getWidth() / 2 || radius > getChildAt(1).getHeight() / 2) {
            if (getChildAt(1).getWidth() > getChildAt(1).getHeight()) {
                radius = getChildAt(1).getHeight() / 2;
            } else {
                radius = getChildAt(1).getWidth() / 2;
            }
        }

        this.imageRound = radius;
        ((RoundImageView) getChildAt(1)).setRound(imageRound);
        invalidate();
        mInvalidat = true;
    }
}

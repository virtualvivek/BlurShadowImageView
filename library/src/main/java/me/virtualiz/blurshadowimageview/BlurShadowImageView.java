/*
 * Copyright 2020 vivekverma
 *
 * Licensed under the MIT License, (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://github.com/virtualvivek/BlurShadowImageView/blob/main/LICENSE
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
import android.graphics.drawable.BitmapDrawable;
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
 * -version：1.2
 * -created ：2019/12/02
 * Developed by：
 * Vivek Verma
 * ================================================
 */
public class BlurShadowImageView extends RelativeLayout {

    private int imageRound = dpToPx(10);
    private int shadowOffset = dpToPx(50);
    private boolean mInvalidat;
    private Bitmap blurredImage;

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

        FadingImageView blurImageView;
        int imageresource;

        setGravity(Gravity.CENTER);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        imageresource = -1;
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

        //---- Layer ImageView ---------------------------------------------------------------------
        RoundImageView roundImageView = new RoundImageView(context);
        roundImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        if (imageresource == -1) {
            roundImageView.setImageResource(android.R.color.transparent);
        } else {
            roundImageView.setImageResource(imageresource);
        }


        //---- Layer blurView ----------------------------------------------------------------------
        blurImageView = new FadingImageView(context);
        blurImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Blurring techinique without renderscript --------------
        if (imageresource == -1) {
            blurImageView.setImageResource(android.R.color.transparent);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageresource);
            blurredImage = Bitmap.createScaledBitmap(bitmap,8,8,true);
            blurImageView.setImageBitmap(blurredImage);
        }


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
        //Setting RoundedImageView layer
        ((RoundImageView) getChildAt(1)).setImageResource(resId);

        //Setting FadedBlurredImageView layer
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
        blurredImage = Bitmap.createScaledBitmap(bitmap,8,8,true);
        ((FadingImageView) getChildAt(0)).setImageBitmap(blurredImage);

        invalidate();
        mInvalidat = true;
    }



    public void setImageDrawable(Drawable drawable) {
        //Setting RoundedImageView layer
        ((RoundImageView) getChildAt(1)).setImageDrawable(drawable);

        //Setting FadedBlurredImageView layer
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        blurredImage = Bitmap.createScaledBitmap(bitmap,8,8,true);
        ((FadingImageView) getChildAt(0)).setImageBitmap(blurredImage);

        invalidate();
        mInvalidat = true;
    }



    public void setImageBitmap(Bitmap bitmap) {
        //Setting RoundedImageView layer
        ((RoundImageView) getChildAt(1)).setImageBitmap(bitmap);


        //Setting FadedBlurredImageView layer
        blurredImage = Bitmap.createScaledBitmap(bitmap,8,8,true);
        ((FadingImageView) getChildAt(0)).setImageBitmap(blurredImage);

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
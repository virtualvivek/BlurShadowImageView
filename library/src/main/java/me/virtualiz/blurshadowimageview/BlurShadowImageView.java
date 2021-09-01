/*
 * Copyright 2021 vivekverma
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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import me.virtualiz.blurshadowimageview.helper.FadingImageView;
import me.virtualiz.blurshadowimageview.helper.RoundImageView;

/**
 * ================================================
 * virtualvivek7@gmail.com
 * -version：4.0
 * -updated ：1 Sep 2021
 * Developed by：
 * Vivek Verma
 * ================================================
 */
public class BlurShadowImageView extends RelativeLayout {

    private int imageRound = dpToPx(10);
    private int shadowOffset = dpToPx(50);
    boolean mInvalidat;
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

        RoundImageView roundImageView = new RoundImageView(context);
        FadingImageView blurImageView = new FadingImageView(context);

        int imageresource, imageScaleTypeIndex;

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
            imageScaleTypeIndex = a.getInt(R.styleable.BlurShadowImageView_android_scaleType, -1);

            if (imageScaleTypeIndex >= 0) {
                ImageView.ScaleType[] types = ImageView.ScaleType.values();
                ImageView.ScaleType scaleType = types[imageScaleTypeIndex];
                roundImageView.setScaleType(scaleType);
            }
            else {
                roundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            a.recycle();
        }
        else {
            float density = context.getResources().getDisplayMetrics().density;
            imageRound = (int) (imageRound * density);
            imageresource = -1;
        }

        //---- Layer ImageView ---------------------------------------------------------------------
        if (imageresource == -1) {
            roundImageView.setImageResource(android.R.color.transparent);
        } else {
            roundImageView.setImageResource(imageresource);
        }


        //---- Layer BlurView ----------------------------------------------------------------------
        blurImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Blurring techinique without renderscript --------------
        if (imageresource == -1) {
            Bitmap image = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
            image.eraseColor(Color.LTGRAY);
            blurImageView.setImageBitmap(image);
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imageresource);

            if (!isInEditMode()) {
                blurredImage = Bitmap.createScaledBitmap(bitmap,8,8,true);
                blurImageView.setImageBitmap(blurredImage);
            }
            //IF is on Edit mode render Basic Shadow to avoid rendering issue
            else {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),bitmap);

                bitmapDrawable.setColorFilter(0xff999999, PorterDuff.Mode.ADD);
                blurImageView.setImageDrawable(bitmapDrawable);
            }
        }

        // Setting Layour For BlurView --
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        blurImageView.setLayoutParams(lp);

        // Setting Layout for RoundImageView --
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lp2.setMargins(dpToPx(15),0,dpToPx(15),shadowOffset);

        roundImageView.setLayoutParams(lp2);

        addView(blurImageView);
        addView(roundImageView);

       getViewTreeObserver().addOnGlobalLayoutListener(()-> {
                int N = getChildCount();
                for (int i = 0; i < N; i++) {
                    N = getChildCount();
                }
                ((RoundImageView) getChildAt(1)).setRound(imageRound);

                mInvalidat = true;
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


    public void setImageRadius(int radius_) {
        if (radius_ > getChildAt(1).getWidth() / 2 || radius_ > getChildAt(1).getHeight() / 2) {
            if (getChildAt(1).getWidth() > getChildAt(1).getHeight()) {
                radius_ = getChildAt(1).getHeight() / 2;
            } else {
                radius_ = getChildAt(1).getWidth() / 2;
            }
        }
        this.imageRound = radius_;
        ((RoundImageView) getChildAt(1)).setRound(imageRound);
        invalidate();
        mInvalidat = true;
    }


}
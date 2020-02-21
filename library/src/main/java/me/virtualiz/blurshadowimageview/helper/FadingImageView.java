package me.virtualiz.blurshadowimageview.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class FadingImageView extends ImageView {

    private boolean mFadeRight;
    private boolean mFadeLeft;
    private boolean mFadeTop;
    private boolean mFadeBottom;

    private Paint paint;
    private Paint paint2;
    private Context c;

    public FadingImageView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);

        this.c = c;
        init();
    }

    public FadingImageView(Context c, AttributeSet attrs) {
        super(c, attrs);

        this.c = c;
        init();
    }

    public FadingImageView(Context c) {
        super(c);

        this.c = c;
        init();
    }

    private void init() {
        // Enable horizontal fading
        this.setHorizontalFadingEdgeEnabled(true);
        this.setVerticalFadingEdgeEnabled(true);
        // Apply default fading length
        this.setEdgeLength(78);
        // Apply default side
        this.setFadeRight(true);
        this.setFadeLeft(true);
        this.setFadeBottom(true);
        this.setFadeTop(true);

        //code to round the fadded view
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    public void setFadeRight(boolean fadeRight) {
        mFadeRight = fadeRight;
    }

    public void setFadeLeft(boolean fadeLeft) {
        mFadeLeft = fadeLeft;
    }

    public void setFadeTop(boolean fadeTop) {
        mFadeTop = fadeTop;
    }

    public void setFadeBottom(boolean fadeBottom) {
        mFadeBottom = fadeBottom;
    }

    public void setEdgeLength(int length) {
        this.setFadingEdgeLength(getPixels(length));
    }


    @Override
    protected float getTopFadingEdgeStrength() {
        return mFadeTop ? 1.0f : 0.0f;
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return mFadeBottom ? 1.0f : 0.0f;
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return mFadeLeft ? 1.0f : 0.0f;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return mFadeRight ? 1.0f : 0.0f;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return true;
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        return false;
    }

    private int getPixels(int dipValue) {
        Resources r = c.getResources();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue, r.getDisplayMetrics());
    }


    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

}
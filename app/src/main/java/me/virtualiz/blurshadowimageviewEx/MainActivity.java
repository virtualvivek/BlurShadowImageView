package me.virtualiz.blurshadowimageviewEx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import me.virtualiz.blurshadowimageview.BlurShadowImageView;
import me.virtualiz.blurshadowimageviewEx.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Button buttonLink = (Button) findViewById(R.id.buttonLink);
        final BlurShadowImageView demo_img = (BlurShadowImageView) findViewById(R.id.demo_img);
        Button load_image_button = (Button) findViewById(R.id.buttonLoadImg);


        demo_img.setImageResource(R.drawable.ironman_80);

        load_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getBaseContext()).load("https://i.imgur.com/DvpvklR.png").into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        demo_img.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable drawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable drawable) {

                    }
                });
            }
        });


        buttonLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.github.com/virtualvivek/BlurShadowImageView");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });



        TextView link_text = (TextView) findViewById(R.id.link_text);
        link_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.github.com/virtualvivek/BlurShadowImageView");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}

package com.kichukkhon.android.travelpartner.Activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;

import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.ImageResizer;

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        imageView = (ImageView) findViewById(R.id.img_big_view);
        String imagePath = getIntent().getStringExtra(Constants.IMAGE_PATH_KEY);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        imageView.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(imagePath,
                width, height));
    }
}

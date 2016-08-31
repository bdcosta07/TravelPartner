package com.kichukkhon.android.travelpartner.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Class.PhotoGallery;
import com.kichukkhon.android.travelpartner.Database.PhotoGalleryDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.ImageResizer;

import java.io.File;

public class ImageDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    PhotoGalleryDBManager dbManager;
    PhotoGallery imageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int imageId = getIntent().getIntExtra(Constants.GALLERY_IMAGE_ID_KEY, 1);

        dbManager = new PhotoGalleryDBManager(this);
        imageInfo = dbManager.getImageInfoById(imageId);

        //set the image
        imageView = (ImageView) findViewById(R.id.img_big_view);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        imageView.setImageBitmap(ImageResizer.decodeSampledBitmapFromFile(imageInfo.getPath(),
                width, height));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_single_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.icon_delete) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Do you really want to delete this picture?");
            alertBuilder.setCancelable(true);

            alertBuilder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //first delete from storage
                            File image = new File(imageInfo.getPath());
                            if (image.exists()) {
                                if (image.delete())
                                    Log.d("TravelPartner", "successfully deleted the image!");
                                else
                                    Log.d("TravelPartner", "sorry! cannot the image");
                            }

                            //delete from db
                            boolean deleted = dbManager.deleteImage(imageInfo.getId());
                            if (deleted) {
                                Intent intent = new Intent(ImageDetailsActivity.this, PhotoGalleryActivity.class);
                                intent.putExtra(Constants.IS_PICTURE_DELETED, true);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ImageDetailsActivity.this, "Something wrong! Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            alertBuilder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}

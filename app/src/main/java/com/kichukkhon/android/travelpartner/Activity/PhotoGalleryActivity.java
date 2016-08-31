package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Adapter.GalleryAdapter;
import com.kichukkhon.android.travelpartner.Class.PhotoGallery;
import com.kichukkhon.android.travelpartner.Database.PhotoGalleryDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.Preference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PhotoGalleryActivity extends BaseDrawerActivity {

    FloatingActionButton fab;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "travelPartner";

    private Uri fileUri = null; // file url to store image
    static File mediaFile = null;

    int currentTourId;
    PhotoGalleryDBManager galleryDBManager;
    GalleryAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        InitCommonUIElements();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Image");

        //Tour
        Preference preference = new Preference(this);
        currentTourId = preference.getCurrentlySelectedTourId();

        galleryDBManager = new PhotoGalleryDBManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rvTourList);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        createTile();

        // Adding Floating Action Button to bottom right of main view
        fab = (FloatingActionButton) findViewById(R.id.fabGallery);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        //if we come here after a image deletion, let's show a snackbar
        if (getIntent() != null) {
            boolean isDeleted = getIntent().getBooleanExtra(Constants.IS_PICTURE_DELETED, false);
            if (isDeleted) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Picture is successfully deleted!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    private void createTile() {
        ArrayList<PhotoGallery> imageList = galleryDBManager.getImageInfoByTourId(currentTourId);
        adapter = new GalleryAdapter(imageList, recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        //recyclerView.setHasFixedSize(true);
        //set padding for tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image
     */
    private File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        //we use 'getExternalFilesDir' as 'getExternalStoragePublicDirectory' does not work
        Log.d("Gallery", mediaStorageDir.getPath());

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            try {
                boolean f = mediaStorageDir.mkdirs();
                Log.d("TP", String.valueOf(f));

            } catch (Exception e) {
                // if any error occurs
                Log.d("TravelPartner", e.getMessage());
                e.printStackTrace();
            }


            /*if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }*/
        }

        // Create a media file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing PhotoGallery
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                saveImageToDB();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled PhotoGallery capture
                Toast.makeText(getApplicationContext(),
                        "You cancelled to capture the image!", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void saveImageToDB() {
        try {
            PhotoGallery photoGallery = new PhotoGallery(System.currentTimeMillis(), fileUri.getPath(), "", currentTourId);

            boolean inserted = galleryDBManager.addImage(photoGallery);
            if (inserted) {
                createTile();
                //adapter.notifyDataSetChanged();
            }


            //Toast.makeText(this,String.valueOf(inserted),Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView dateTaken;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.image_gallery_row, parent, false));

            picture = (ImageView) itemView.findViewById(R.id.image);
            dateTaken = (TextView) itemView.findViewById(R.id.txtDateTaken);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    *//*Context context = view.getContext();
                    PlaceTypes placeTypes = new PlaceTypes();
                    Intent intent = new Intent(context, Places_list.class);
                    String place = placeTypes.place_type_list[getAdapterPosition()];
                    intent.putExtra(Constants.PLACE_TYPE_ID_KEY, place);
                    context.startActivity(intent);*//*
                }
            });
        }
    }*/

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
}

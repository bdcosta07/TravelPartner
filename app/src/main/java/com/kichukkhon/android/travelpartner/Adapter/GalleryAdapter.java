package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.PhotoGallery;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

import java.util.ArrayList;

/**
 * Created by Ratul on 8/30/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    ArrayList<PhotoGallery> imageList = new ArrayList<>();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvDateTaken;

        public ViewHolder(View itemView) {
            super(itemView);

            //TODO onclicklistener for itemview

            imageView = (ImageView) itemView.findViewById(R.id.gallery_image);
            tvDateTaken = (TextView) itemView.findViewById(R.id.tvDateTaken);
        }
    }

    public GalleryAdapter(ArrayList<PhotoGallery> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_gallery_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        long dateTaken = imageList.get(position).getDateTime();
        StringBuilder sb = new StringBuilder();

        sb.append(AppUtils.getFormattedDate(context, dateTaken));
        sb.append(" at ");
        sb.append(AppUtils.getFormattedTime(context, dateTaken));

        holder.tvDateTaken.setText(sb.toString());
        String imagePath = imageList.get(position).getPath();
        Bitmap image = getResizedBitmap(holder, imagePath);
        holder.imageView.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    private Bitmap getResizedBitmap(ViewHolder viewHolder, String imagePath) {

		/* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = viewHolder.imageView.getWidth() == 0 ? 500 : viewHolder.imageView.getWidth();
        int targetH = viewHolder.imageView.getHeight() == 0 ? 180 : viewHolder.imageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        return bitmap;
    }
}

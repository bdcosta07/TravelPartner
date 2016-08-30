package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Activity.ImageDetailsActivity;
import com.kichukkhon.android.travelpartner.Class.PhotoGallery;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.ImageResizer;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageDetailsActivity.class);
                    int imageId = imageList.get(getAdapterPosition()).getId();
                    intent.putExtra(Constants.GALLERY_IMAGE_ID_KEY, imageId);
                    context.startActivity(intent);
                }
            });

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
        int targetW = holder.imageView.getWidth() == 0 ? 500 : holder.imageView.getWidth();
        int targetH = holder.imageView.getHeight() == 0 ? 180 : holder.imageView.getHeight();
        Bitmap image = ImageResizer.decodeSampledBitmapFromFile(imagePath, targetW, targetH);
        holder.imageView.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
